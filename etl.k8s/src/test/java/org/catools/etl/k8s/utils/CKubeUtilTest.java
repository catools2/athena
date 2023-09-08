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
    CEtlKubeLoader.loadPods("Test", pods);

    CEtlKubePod podFromDB = CEtlKubeBaseDao.find(CEtlKubePod.class, 1L);

    CVerify.Object.isNull(podFromDB.getHostname());

    CVerify.Object.isNotNull(podFromDB.getNodeName(), "docker-desktop");
    CVerify.String.equals(podFromDB.getStatus().getPhase(), "Running");
    CVerify.String.equals(podFromDB.getStatus().getType(), "10.1.0.7");

    CVerify.String.equals(podFromDB.getProject().getName(), "Test");

    CEtlKubeContainer kubeContainer = podFromDB.getContainers().stream().findFirst().get();
    CVerify.String.equals(kubeContainer.getType(), "eternal");
    CVerify.String.equals(kubeContainer.getName(), "jenkins");
    CVerify.String.equals(kubeContainer.getImage(), "bitnami/jenkins:2.401.3-debian-11-r0");
    CVerify.String.equals(kubeContainer.getImageId(), "docker-pullable://bitnami/jenkins@sha256:31145a485766bb3cf7aa6a3cbf1bcc39269231aec04e4102325b58410747f240");
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