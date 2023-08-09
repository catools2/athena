package org.catools.k8s.utils;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1PodList;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.utils.CJsonUtil;
import org.catools.common.utils.CResourceUtil;
import org.catools.k8s.model.CKubePods;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CKubeUtilTest {

  @Test
  public void testReadXSSFWorksheetWithoutHeader() throws ApiException {
    CoreV1Api api = mock(CoreV1Api.class);
    String podListData = CResourceUtil.getString("podList.json", CKubeUtilTest.class);
    V1PodList read = CJsonUtil.read(podListData, V1PodList.class);
    when(api.listNamespacedPod(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
        .thenReturn(read);
    CKubePods pods = CKubeUtil.getNamespacePods(api, "default");
    CVerify.Int.equals(pods.size(), 1, "Pod size is correct");
  }
}