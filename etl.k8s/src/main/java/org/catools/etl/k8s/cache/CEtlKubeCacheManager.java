package org.catools.etl.k8s.cache;

import lombok.experimental.UtilityClass;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.etl.k8s.dao.CEtlKubeContainerMetaDataDao;
import org.catools.etl.k8s.dao.CEtlKubePodMetaDataDao;
import org.catools.etl.k8s.dao.CEtlKubePodStatusDao;
import org.catools.etl.k8s.dao.CEtlKubeProjectDao;
import org.catools.etl.k8s.model.CEtlKubeContainerMetaData;
import org.catools.etl.k8s.model.CEtlKubePodMetaData;
import org.catools.etl.k8s.model.CEtlKubePodStatus;
import org.catools.etl.k8s.model.CEtlKubeProject;

import java.util.function.Supplier;

@UtilityClass
public class CEtlKubeCacheManager {
  private static final CMap<String, CEtlKubeProject> PROJECTS = new CHashMap<>();
  private static final CMap<String, CEtlKubePodStatus> STATUSES = new CHashMap<>();
  private static final CMap<String, CEtlKubePodMetaData> POD_METADATA = new CHashMap<>();
  private static final CMap<String, CEtlKubeContainerMetaData> CONTAINER_METADATA = new CHashMap<>();

  /**
   * Get the project from DB if exists otherwise create one and save it in local cash for farther usage
   *
   * @param name
   * @return
   */
  public static synchronized CEtlKubeProject getProject(String name) {
    return read(PROJECTS, name, () -> {
      CEtlKubeProject result = CEtlKubeProjectDao.getProjectByName(name);
      if (result != null) {
        return result;
      }
      return CEtlKubeProjectDao.merge(new CEtlKubeProject(name));
    });
  }

  /**
   * Get the status from DB if exists otherwise create one and save it in local cash for farther usage
   *
   * @param status
   * @param type
   * @param phase
   * @param message
   * @param reason
   * @return
   */
  public static CEtlKubePodStatus getStatus(String status, String type, String phase, String message, String reason) {
    return read(STATUSES, status + type + phase + message + reason, () -> {
      CEtlKubePodStatus result = CEtlKubePodStatusDao.getEtlKubePodStatus(status, type, phase, message, reason);
      if (result != null) {
        return result;
      }
      return CEtlKubePodStatusDao.merge(new CEtlKubePodStatus(status, type, phase, message, reason));
    });
  }

  /**
   * Get the pod metadata from DB if exists otherwise create one and save it in local cash for farther usage
   *
   * @param type
   * @param name
   * @param value
   * @return
   */
  public static CEtlKubePodMetaData getPodMetadata(String type, String name, String value) {
    return read(POD_METADATA, type + name + value, () -> {
      CEtlKubePodMetaData result = CEtlKubePodMetaDataDao.getKubePodMetaData(type, name, value);
      if (result != null) {
        return result;
      }
      return CEtlKubePodMetaDataDao.merge(new CEtlKubePodMetaData(type, name, value));
    });
  }

  /**
   * Get the container from DB if exists otherwise create one and save it in local cash for farther usage
   *
   * @param name
   * @param value
   * @return
   */
  public static CEtlKubeContainerMetaData getContainerMetadata(String name, String value) {
    return read(CONTAINER_METADATA, name + value, () -> {
      CEtlKubeContainerMetaData result = CEtlKubeContainerMetaDataDao.getKubeContainerMetaData(name, value);
      if (result != null) {
        return result;
      }
      return CEtlKubeContainerMetaDataDao.merge(new CEtlKubeContainerMetaData(name, value));
    });
  }

  private static synchronized <T> T read(CMap<String, T> storage, String key, Supplier<T> getValue) {
    return storage.computeIfAbsent(key, k -> getValue.get());
  }
}
