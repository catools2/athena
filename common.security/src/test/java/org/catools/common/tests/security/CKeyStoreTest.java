package org.catools.common.tests.security;

import org.catools.common.configs.CPathConfigs;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.common.security.CKeyStore;
import org.catools.common.security.CKeyStoreException;
import org.catools.common.tests.CBaseUnitTest;
import org.testng.annotations.Test;

import java.security.cert.X509Certificate;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;

public class CKeyStoreTest extends CBaseUnitTest {
  private final String PASSWORD = "passw0rd";
  private final String CATOOLS = "cats";
  private CKeyStore keyStore =
      new CKeyStore("testData/CCertification/catools.jks", CKeyStoreTest.class, PASSWORD);
  private CFile keyStoreFile =
      new CResource("testData/CCertification/catools.jks", CKeyStoreTest.class)
          .saveToFolder(CPathConfigs.getTempFolder());

  @Test
  public void testConstructorUsingFile() {
    CKeyStore keyStore = new CKeyStore(keyStoreFile, PASSWORD);
    X509Certificate cert = keyStore.getX509Certificate(CATOOLS);
    CVerify.String.contains(
        cert.getSubjectX500Principal().getName(),
        "OU=CATS,O=CAF,C=US",
        "Subject information is correct");
    CVerify.String.contains(
        cert.getIssuerDN().getName(), "OU=CATS, O=CAF, C=US", "Issuer DN information is correct");
  }

  @Test
  public void testConstructorUsingStream() {
    CKeyStore keyStore = new CKeyStore(keyStoreFile.getInputStream(), PASSWORD);
    X509Certificate cert = keyStore.getX509Certificate(CATOOLS);
    CVerify.String.contains(
        cert.getSubjectX500Principal().getName(),
        "OU=CATS,O=CAF,C=US",
        "Subject information is correct");
    CVerify.String.contains(
        cert.getIssuerDN().getName(), "OU=CATS, O=CAF, C=US", "Issuer DN information is correct");
  }

  @Test(expectedExceptions = CKeyStoreException.class)
  public void testConstructorUsingStream_BadPassword() {
    new CKeyStore(keyStoreFile.getInputStream(), PASSWORD + 1);
  }

  @Test
  public void testGetCertificate() {
    X509Certificate cert = keyStore.getX509Certificate(CATOOLS);
    CVerify.String.contains(
        cert.getSubjectX500Principal().getName(),
        "OU=CATS,O=CAF,C=US",
        "Subject information is correct");
    CVerify.String.contains(
        cert.getIssuerDN().getName(), "OU=CATS, O=CAF, C=US", "Issuer DN information is correct");
  }

  @Test
  public void testGetCertificate_BadAlias() {
    CVerify.Object.isNull(keyStore.getX509Certificate(CATOOLS + 1), "No certification returned");
  }

  @Test
  public void testGetPrivate() {
    DSAPrivateKey privateKey = keyStore.getPrivate(CATOOLS, PASSWORD);
    DSAParams params = privateKey.getParams();
    CVerify.String.equals(
        privateKey.getX().toString(),
        "7461348582853775916850805281956727836139466571597306866156502995412",
        "X is correct");
    CVerify.String.equals(
        params.getP().toString(),
        "18111848663142005571178770624881214696591339256823507023544605891411707081617152319519180201250440615163700426054396403795303435564101919053459832890139496933938670005799610981765220283775567361483662648340339405220348871308593627647076689407931875483406244310337925809427432681864623551598136302441690546585427193224254314088256212718983105131138772434658820375111735710449331518776858786793875865418124429269409118756812841019074631004956409706877081612616347900606555802111224022921017725537417047242635829949739109274666495826205002104010355456981211025738812433088757102520562459649777989718122219159982614304359",
        "P is correct");
    CVerify.String.equals(
        params.getQ().toString(),
        "19689526866605154788513693571065914024068069442724893395618704484701",
        "Q is correct");
    CVerify.String.equals(
        params.getG().toString(),
        "2859278237642201956931085611015389087970918161297522023542900348087718063098423976428252369340967506010054236052095950169272612831491902295835660747775572934757474194739347115870723217560530672532404847508798651915566434553729839971841903983916294692452760249019857108409189016993380919900231322610083060784269299257074905043636029708121288037909739559605347853174853410208334242027740275688698461842637641566056165699733710043802697192696426360843173620679214131951400148855611740858610821913573088059404459364892373027492936037789337011875710759208498486908611261954026964574111219599568903257472567764789616958430",
        "G is correct");
  }

  @Test
  public void testGetPrivate_BadAlias() {
    CVerify.Object.isNull(keyStore.getPrivate(CATOOLS + 1, PASSWORD), "No Key returned");
  }

  @Test(expectedExceptions = CKeyStoreException.class)
  public void testGetPrivate_BadPassword() {
    keyStore.getPrivate(CATOOLS, PASSWORD + 1);
  }

  @Test
  public void testGetPublic() {
    CVerify.String.equals(
        ((DSAPublicKey) keyStore.getPublic(CATOOLS)).getY().toString(),
        "5216587289848430634584420912099945299591545291858845142083726357466738902186667948942303264499362000568967695866021333820509542497617043189587207915850405683648677655292348469556238056697879508271839556025252439888199585692710376458062566350874828494187982050078895016711750514496067956295169732130545843949503038737064253695544417061991295453080002251505795015484351194929346602712043277883178505473526613476796140074476492493398420808539151687317773174384083086244879770485023349143964498182615931761006024078129447301785069175910936472732380563886322848817710138442897389874211387978358891107472670022931682442026",
        "Y is correct");
  }
}
