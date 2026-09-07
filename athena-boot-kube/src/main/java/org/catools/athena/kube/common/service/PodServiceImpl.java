package org.catools.athena.kube.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.kube.common.mapper.KubeMapper;
import org.catools.athena.kube.common.mapper.KubeMapperService;
import org.catools.athena.kube.common.model.Container;
import org.catools.athena.kube.common.model.Pod;
import org.catools.athena.kube.common.model.PodStatus;
import org.catools.athena.kube.common.repository.ContainerMetadataRepository;
import org.catools.athena.kube.common.repository.MetadataRepository;
import org.catools.athena.kube.common.repository.PodAnnotationRepository;
import org.catools.athena.kube.common.repository.PodLabelRepository;
import org.catools.athena.kube.common.repository.PodMetadataRepository;
import org.catools.athena.kube.common.repository.PodRepository;
import org.catools.athena.kube.common.repository.PodSelectorRepository;
import org.catools.athena.kube.common.repository.PodStatusRepository;
import org.catools.athena.model.core.NameValuePair;
import org.catools.athena.model.kube.PodDto;
import org.catools.athena.model.kube.PodInventoryDto;
import org.catools.athena.model.kube.PodSummaryDto;
import org.catools.athena.model.kube.PodTrendPointDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PodServiceImpl implements PodService {

  private final ContainerMetadataRepository containerMetadataRepository;
  private final PodAnnotationRepository podAnnotationRepository;
  private final PodMetadataRepository podMetadataRepository;
  private final PodSelectorRepository podSelectorRepository;
  private final PodStatusRepository podStatusRepository;
  private final PodLabelRepository podLabelRepository;
  private final PodRepository podRepository;
  private final KubeMapperService kubeMapperService;
  private final KubeMapper kubeMapper;

  @Override
  @Transactional
  public PodDto saveOrUpdate(PodDto entity) {
    log.debug("Saving entity: {}", entity);
    final Pod pod = kubeMapper.podDtoToPod(entity);
    final Pod podToSave = podRepository.findByNameAndNamespace(entity.getName(), entity.getNamespace())
        .map(p -> {
          p.setName(pod.getName());
          p.setHostname(pod.getHostname());
          p.setNamespace(pod.getNamespace());
          p.setNodeName(pod.getNodeName());
          p.setCreatedAt(pod.getCreatedAt());
          p.setDeletedAt(pod.getDeletedAt());

          p.setProjectId(pod.getProjectId());

          normalizeRelationships(p, pod);

          pod.getContainers()
              .stream()
              .filter(d1 -> p.getContainers().stream().noneMatch(d2 -> d1.getName().equals(d2.getName())))
              .forEach(p::addContainer);

          return p;
        }).orElseGet(() -> {
          normalizeRelationships(pod, pod);
          return pod;
        });

    final Pod savedRecord = RetryUtils.retry(3, 1000, integer -> podRepository.saveAndFlush(podToSave));
    return kubeMapper.podToPodDto(savedRecord);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<PodDto> getById(Long id) {
    return podRepository.findById(id).map(kubeMapper::podToPodDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Set<PodDto> getPods(String project, String namespace) {
    // Use query with join on project code; eliminates kubeMapperService.getProjectId() call
    Set<Pod> pods;
    if (project != null && !project.isBlank()) {
      Long projectId = kubeMapperService.getProjectId(project);
      pods = podRepository.findByProjectIdAndNamespace(projectId, namespace);
    } else {
      pods = podRepository.findByNamespace(namespace);
    }
    return pods.stream().map(kubeMapper::podToPodDto).collect(Collectors.toSet());
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<PodDto> getByNameAndNamespace(String name, String namespace) {
    return podRepository.findByNameAndNamespace(name, namespace).map(kubeMapper::podToPodDto);
  }

    @Override
    @Transactional(readOnly = true)
    public Page<PodInventoryDto> getAll(
      Pageable pageable,
      String project,
      String namespace,
      String name,
      String nodeName,
      String status
    ) {
    final List<PodDashboardRow> filteredRows = getFilteredRows(project, namespace, name, nodeName, status);
    final Comparator<PodDashboardRow> comparator = getDashboardComparator(pageable.getSort());
    final List<PodInventoryDto> sortedRows = filteredRows.stream()
      .sorted(comparator)
      .map(this::toInventoryDto)
      .toList();

    final int startIndex = Math.min((int) pageable.getOffset(), sortedRows.size());
    final int endIndex = Math.min(startIndex + pageable.getPageSize(), sortedRows.size());
    return new PageImpl<>(sortedRows.subList(startIndex, endIndex), pageable, sortedRows.size());
    }

    @Override
    @Transactional(readOnly = true)
    public PodSummaryDto getSummary(
      String project,
      String namespace,
      String name,
      String nodeName,
      String status,
      Integer windowDays
    ) {
    final List<PodDashboardRow> filteredRows = applyWindow(getFilteredRows(project, namespace, name, nodeName, status), PodDashboardRow::lastSync, windowDays);
    return new PodSummaryDto()
      .setTotalCount((long) filteredRows.size())
      .setNamespaceCount(filteredRows.stream().map(PodDashboardRow::namespace).filter(StringUtils::isNotBlank).distinct().count())
      .setNodeCount(filteredRows.stream().map(PodDashboardRow::nodeName).filter(StringUtils::isNotBlank).distinct().count())
      .setActiveCount(filteredRows.stream().filter(row -> row.deletedAt() == null).count())
      .setDeletedCount(filteredRows.stream().filter(row -> row.deletedAt() != null).count())
      .setLatestSyncTime(filteredRows.stream().map(PodDashboardRow::lastSync).filter(Objects::nonNull).max(Comparator.naturalOrder()).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PodTrendPointDto> getTrend(
      String project,
      String namespace,
      String name,
      String nodeName,
      String status,
      Integer windowDays
    ) {
    final Map<Instant, List<PodDashboardRow>> buckets = getFilteredRows(project, namespace, name, nodeName, status).stream()
      .filter(row -> row.lastSync() != null)
      .collect(Collectors.groupingBy(
        row -> row.lastSync().truncatedTo(ChronoUnit.DAYS),
        TreeMap::new,
        Collectors.toList()
      ));

    final List<PodTrendPointDto> trendPoints = buckets.entrySet().stream()
      .map(entry -> new PodTrendPointDto()
        .setBucketStart(entry.getKey())
        .setPodCount((long) entry.getValue().size())
        .setActiveCount(entry.getValue().stream().filter(row -> row.deletedAt() == null).count())
        .setDeletedCount(entry.getValue().stream().filter(row -> row.deletedAt() != null).count())
        .setUniqueNamespaceCount(entry.getValue().stream().map(PodDashboardRow::namespace).filter(StringUtils::isNotBlank).distinct().count()))
      .toList();

    return applyTrendWindow(trendPoints, windowDays);
    }

  private List<PodTrendPointDto> applyTrendWindow(List<PodTrendPointDto> trendPoints, Integer windowDays) {
    if (windowDays == null || windowDays < 1 || trendPoints.isEmpty()) {
      return trendPoints;
    }

    final Instant latestBucketStart = trendPoints.stream()
        .map(PodTrendPointDto::getBucketStart)
        .filter(Objects::nonNull)
        .max(Comparator.naturalOrder())
        .orElse(null);

    if (latestBucketStart == null) {
      return trendPoints;
    }

    final Instant threshold = latestBucketStart.minus(Math.max(windowDays - 1L, 0L), ChronoUnit.DAYS);
    return trendPoints.stream()
        .filter(point -> point.getBucketStart() != null && !point.getBucketStart().isBefore(threshold))
        .toList();
  }

  private <T> List<T> applyWindow(List<T> rows, Function<T, Instant> timestampExtractor, Integer windowDays) {
    if (windowDays == null || windowDays < 1 || rows.isEmpty()) {
      return rows;
    }

    final Instant latestTimestamp = rows.stream()
        .map(timestampExtractor)
        .filter(Objects::nonNull)
        .max(Comparator.naturalOrder())
        .orElse(null);

    if (latestTimestamp == null) {
      return List.of();
    }

    final Instant threshold = latestTimestamp.truncatedTo(ChronoUnit.DAYS).minus(Math.max(windowDays - 1L, 0L), ChronoUnit.DAYS);
    return rows.stream()
        .filter(row -> {
          final Instant timestamp = timestampExtractor.apply(row);
          return timestamp != null && !timestamp.isBefore(threshold);
        })
        .toList();
  }

  private void normalizeRelationships(Pod target, Pod source) {
    target.setStatus(normalizePodStatus(source.getStatus()));

    target.setMetadata(normalizeMetadata(source.getMetadata(), podMetadataRepository));
    target.setAnnotations(normalizeMetadata(source.getAnnotations(), podAnnotationRepository));
    target.setLabels(normalizeMetadata(source.getLabels(), podLabelRepository));
    target.setSelectors(normalizeMetadata(source.getSelectors(), podSelectorRepository));

    for (Container container : source.getContainers()) {
      container.setPod(source);
      container.setMetadata(normalizeMetadata(container.getMetadata(), containerMetadataRepository));
    }
  }

  private synchronized PodStatus normalizePodStatus(PodStatus status) {
    return podStatusRepository.findByNameAndPhaseAndMessageAndReason(status.getName(), status.getPhase(), status.getMessage(), status.getReason())
        .orElseGet(() -> RetryUtils.retry(3, 1000, integer -> podStatusRepository.saveAndFlush(status)));
  }

  public <T extends NameValuePair> Set<T> normalizeMetadata(Set<T> metadataSet, MetadataRepository<T> metadataRepository) {
    final Set<T> metadata = new HashSet<>();

    for (T md : metadataSet) {
      try {
        // Read md from DB and if MD does not exist we create one and assign it
        T normalizedMd = metadataRepository.findByNameAndValue(md.getName(), md.getValue())
            .orElseGet(() -> {
              // Let the database absorb the race. A plain insert here would abort the surrounding
              // transaction on conflict, and a recovery lookup issued afterwards could never run --
              // PostgreSQL refuses every statement in an aborted transaction, which surfaced as an
              // opaque Hibernate "null identifier" assertion rather than the 23505 that caused it.
              metadataRepository.insertIfAbsent(md.getName(), md.getValue());
              return metadataRepository.findByNameAndValue(md.getName(), md.getValue())
                  .orElseThrow(() -> new IllegalStateException(
                      "Metadata (" + md.getName() + ") could not be read back after insert"));
            });

        metadata.add(normalizedMd);
      } catch (Exception e) {
        throw new RuntimeException("Failed to create detached instance", e);
      }
    }

    return metadata;
  }

  private List<PodDashboardRow> getFilteredRows(
      String project,
      String namespace,
      String name,
      String nodeName,
      String status
  ) {
    return podRepository.findAll().stream()
        .map(kubeMapper::podToPodDto)
        .map(this::toDashboardRow)
        .filter(row -> matchesFilter(row.project(), project))
        .filter(row -> matchesFilter(row.namespace(), namespace))
        .filter(row -> matchesFilter(row.name(), name))
        .filter(row -> matchesFilter(row.nodeName(), nodeName))
        .filter(row -> matchesFilter(row.status(), status))
        .toList();
  }

  private Comparator<PodDashboardRow> getDashboardComparator(Sort sort) {
    if (sort == null || sort.isUnsorted()) {
      return compareRows(PodDashboardRow::lastSync, true);
    }

    Comparator<PodDashboardRow> comparator = null;
    for (Sort.Order order : sort) {
      final Comparator<PodDashboardRow> orderComparator = switch (order.getProperty()) {
        case "project" -> compareRows(PodDashboardRow::project, order.isDescending());
        case "namespace" -> compareRows(PodDashboardRow::namespace, order.isDescending());
        case "name" -> compareRows(PodDashboardRow::name, order.isDescending());
        case "nodeName" -> compareRows(PodDashboardRow::nodeName, order.isDescending());
        case "status" -> compareRows(PodDashboardRow::status, order.isDescending());
        case "containerCount" -> compareRows(PodDashboardRow::containerCount, order.isDescending());
        case "createdAt" -> compareRows(PodDashboardRow::createdAt, order.isDescending());
        case "deletedAt" -> compareRows(PodDashboardRow::deletedAt, order.isDescending());
        default -> compareRows(PodDashboardRow::lastSync, order.isDescending());
      };
      comparator = comparator == null ? orderComparator : comparator.thenComparing(orderComparator);
    }

    return comparator == null ? compareRows(PodDashboardRow::lastSync, true) : comparator;
  }

  private PodDashboardRow toDashboardRow(PodDto pod) {
    return new PodDashboardRow(
        pod.getId(),
        pod.getProject(),
        pod.getNamespace(),
        pod.getName(),
        pod.getNodeName(),
        resolveStatus(pod),
        pod.getContainers() == null ? 0 : pod.getContainers().size(),
        pod.getCreatedAt(),
        pod.getDeletedAt(),
        pod.getLastSync());
  }

  private PodInventoryDto toInventoryDto(PodDashboardRow row) {
    return new PodInventoryDto()
        .setId(row.id())
        .setProject(row.project())
        .setNamespace(row.namespace())
        .setName(row.name())
        .setNodeName(row.nodeName())
        .setStatus(row.status())
        .setContainerCount(row.containerCount())
        .setCreatedAt(row.createdAt())
        .setDeletedAt(row.deletedAt())
        .setLastSync(row.lastSync());
  }

  private static String resolveStatus(PodDto pod) {
    if (pod == null || pod.getStatus() == null) {
      return null;
    }

    return StringUtils.firstNonBlank(pod.getStatus().getName(), pod.getStatus().getPhase(), pod.getStatus().getReason());
  }

  private static boolean matchesFilter(String value, String filter) {
    return StringUtils.isBlank(filter) || StringUtils.containsIgnoreCase(StringUtils.defaultString(value), filter.trim());
  }

  private static <T extends Comparable<? super T>> Comparator<PodDashboardRow> compareRows(
      Function<PodDashboardRow, T> extractor,
      boolean descending
  ) {
    final Comparator<PodDashboardRow> comparator = Comparator.comparing(extractor, Comparator.nullsLast(Comparator.naturalOrder()));
    return descending ? comparator.reversed() : comparator;
  }

  private record PodDashboardRow(
      Long id,
      String project,
      String namespace,
      String name,
      String nodeName,
      String status,
      Integer containerCount,
      Instant createdAt,
      Instant deletedAt,
      Instant lastSync
  ) {
  }
}
