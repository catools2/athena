package org.catools.k8s.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.catools.common.collections.CList;

@Data
@Accessors(chain = true)
public class CKubeContainers extends CList<CKubeContainer> {

}
