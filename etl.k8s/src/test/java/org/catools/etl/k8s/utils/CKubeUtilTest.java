package org.catools.etl.k8s.utils;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1PodList;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.hocon.CHocon;
import org.catools.common.utils.CJsonUtil;
import org.catools.common.utils.CResourceUtil;
import org.catools.etl.k8s.dao.CEtlKubeBaseDao;
import org.catools.etl.k8s.model.CEtlKubeContainer;
import org.catools.etl.k8s.model.CEtlKubePod;
import org.catools.k8s.model.CKubePods;
import org.catools.k8s.utils.CKubeUtil;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CKubeUtilTest {

  @Test
  public void testReadXSSFWorksheetWithoutHeader() throws ApiException {
    CHocon.reload();
    CKubePods pods = getKubePods();
    CEtlKubeLoader.loadPods("Test", pods, 1);

    CEtlKubePod podFromDB = CEtlKubeBaseDao.find(CEtlKubePod.class, pods.get(0).getName());

    CVerify.Object.isNull(podFromDB.getHostname());

    CVerify.Object.isNotNull(podFromDB.getNodeName(), "docker-desktop");
    CVerify.String.equals(podFromDB.getStatus().getPhase(), "Running");

    CVerify.String.equals(podFromDB.getProject().getName(), "Test");

    CEtlKubeContainer kubeContainer = podFromDB.getContainers().stream().findFirst().get();
    CVerify.String.equals(kubeContainer.getType(), "eternal");
    CVerify.String.equals(kubeContainer.getName(), "jenkins");
    CVerify.String.equals(kubeContainer.getImage(), "bitnami/jenkins:2.414.1-debian-11-r0");
    CVerify.String.equals(kubeContainer.getImageId(), "docker-pullable://bitnami/jenkins@sha256:9ad7463a83a56b5fd9038c1366b5dbb3b2d41a6eec3c39e0beab567c85397fce");
    CVerify.Bool.isTrue(kubeContainer.getStarted());
    CVerify.Bool.isTrue(kubeContainer.getReady());

    CVerify.Collection.verifySizeEquals(podFromDB.getMetadata(), 7);
  }

  private static CKubePods getKubePods() throws ApiException {
    CoreV1Api api = mock(CoreV1Api.class);
    String podListData = CResourceUtil.getString("podList.json", CKubeUtilTest.class);
    V1PodList read = CJsonUtil.read(podListData, V1PodList.class);
    when(api.listNamespacedPod(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(read);
    CKubePods pods = CKubeUtil.getNamespacePods(api, "default");
    return pods;
  }
}