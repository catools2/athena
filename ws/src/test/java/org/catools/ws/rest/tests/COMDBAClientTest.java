package org.catools.ws.rest.tests;

import org.assertj.core.api.Assertions;
import org.catools.common.tests.CTest;
import org.catools.ws.rest.tests.api.clients.omd.OmdbGetByIdApiClient;
import org.catools.ws.rest.tests.api.clients.omd.OmdbGetByTitleApiClient;
import org.catools.ws.rest.tests.api.clients.omd.OmdbSearchApiClient;
import org.catools.ws.rest.tests.api.entities.MovieDetail;
import org.catools.ws.rest.tests.api.entities.Movies;
import org.catools.ws.rest.tests.api.entities.SearchParam;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class COMDBAClientTest extends CTest {

  /**
   * Base method to verify search end point functionality.
   * I moved method to the base class so I can use it for different test implementation I have
   *
   * @param search         the search criteria to send to search end point
   * @param expectedTitles the expected titles which should be in the response result set
   */
  protected void verifySearchMovieTitleInResponse(String search, String[] expectedTitles) {
    Movies response = new OmdbSearchApiClient(Map.of(SearchParam.SEARCH, search)).readAllPages();
    for (String expectedTitle : expectedTitles) {
      boolean responseContains = response.has(t -> expectedTitle.equals(t.getTitle()));
      Assertions.assertThat(responseContains).isTrue();
    }
  }

  @DataProvider(name = "Search")
  public Object[][] dpSearch() {
    return new Object[][]{
        {"stem", new String[]{"The STEM Journals", "Activision: STEM - in the Videogame Industry"}}
    };
  }

  @Test(dataProvider = "Search")
  public void verifySearchMethodResponseContainsExpectedTitles(String search, String[] expectedTitles) {
    verifySearchMovieTitleInResponse(search, expectedTitles);
  }


  @DataProvider(name = "TotalResult")
  public Object[][] dpTotalResult() {
    return new Object[][]{
        {"stem", 30}
    };
  }

  @Test(dataProvider = "TotalResult")
  public void verifySearchMethodResponseHasExpectedTotalResult(String search, int expectedMinSize) {
    Movies response = new OmdbSearchApiClient(Map.of(SearchParam.SEARCH, search)).readAllPages();
    Assertions.assertThat(response).hasSizeGreaterThan(expectedMinSize);
  }

  @DataProvider(name = "ById")
  public Object[][] dpById() {
    return new Object[][]{
        {"stem", "Activision: STEM - in the Videogame Industry", "23 Nov 2010", "Mike Feurstein"}
    };
  }

  @Test(dataProvider = "ById")
  public void verifyGetByIdReturnsExpectedRecord(String search, String title, String released, String director) {
    Movies response = new OmdbSearchApiClient(Map.of(SearchParam.SEARCH, search)).readAllPages().getByTitle(title);
    Assertions.assertThat(response.size()).isEqualTo(1);
    MovieDetail movieDetailById = new OmdbGetByIdApiClient(response.get(0).getImdbID()).process();
    Assertions.assertThat(movieDetailById.getReleased()).isEqualTo(released);
    Assertions.assertThat(movieDetailById.getDirector()).isEqualTo(director);
  }


  @DataProvider(name = "ByTitle")
  public Object[][] dpByTitle() {
    return new Object[][]{
        {"The STEM Journals", "Science, Technology, Engineering and Math", "22 min"}
    };
  }

  @Test(dataProvider = "ByTitle")
  public void verifyGetByTitleReturnsExpectedRecord(String title, String plot, String runtime) {
    MovieDetail movieDetailByTitle = new OmdbGetByTitleApiClient(title).process();
    Assertions.assertThat(movieDetailByTitle.getPlot()).containsIgnoringCase(plot);
    Assertions.assertThat(movieDetailByTitle.getRuntime()).isEqualTo(runtime);
  }
}