package org.catools.common.tests.utils;

import org.catools.common.utils.CAsciiArtUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CAsciiArtUtilTest {

  @Test
  public void testConvertWithDoh() {
    Assert.assertEquals(
        String.join("\n", CAsciiArtUtil.convertWithDoh("CAT")),
        ""
            + "        CCCCCCCCCCCCC               AAA               TTTTTTTTTTTTTTTTTTTTTTT\n"
            + "     CCC::::::::::::C              A:::A              T:::::::::::::::::::::T\n"
            + "   CC:::::::::::::::C             A:::::A             T:::::::::::::::::::::T\n"
            + "  C:::::CCCCCCCC::::C            A:::::::A            T:::::TT:::::::TT:::::T\n"
            + " C:::::C       CCCCCC           A:::::::::A           TTTTTT  T:::::T  TTTTTT\n"
            + "C:::::C                        A:::::A:::::A                  T:::::T        \n"
            + "C:::::C                       A:::::A A:::::A                 T:::::T        \n"
            + "C:::::C                      A:::::A   A:::::A                T:::::T        \n"
            + "C:::::C                     A:::::A     A:::::A               T:::::T        \n"
            + "C:::::C                    A:::::AAAAAAAAA:::::A              T:::::T        \n"
            + "C:::::C                   A:::::::::::::::::::::A             T:::::T        \n"
            + " C:::::C       CCCCCC    A:::::AAAAAAAAAAAAA:::::A            T:::::T        \n"
            + "  C:::::CCCCCCCC::::C   A:::::A             A:::::A         TT:::::::TT      \n"
            + "   CC:::::::::::::::C  A:::::A               A:::::A        T:::::::::T      \n"
            + "     CCC::::::::::::C A:::::A                 A:::::A       T:::::::::T      \n"
            + "        CCCCCCCCCCCCCAAAAAAA                   AAAAAAA      TTTTTTTTTTT      \n",
        "CAsciiArtUtil works fine");
  }
}
