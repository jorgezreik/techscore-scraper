import org.jsoup.*;
import org.jsoup.helper.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Scraper {

	public static void main (String args[]) {
    // All urls
    String[] seasons = new String[] {
      "https://scores.collegesailing.org/schools/princeton/f19/",
      "https://scores.collegesailing.org/schools/princeton/s19/",
      "https://scores.collegesailing.org/schools/princeton/f18/",
      "https://scores.collegesailing.org/schools/princeton/s18/",
      "https://scores.collegesailing.org/schools/princeton/f17/",
      "https://scores.collegesailing.org/schools/princeton/s17/",
      "https://scores.collegesailing.org/schools/princeton/f16/",
      "https://scores.collegesailing.org/schools/princeton/s16/",
      "https://scores.collegesailing.org/schools/princeton/f15/",
      "https://scores.collegesailing.org/schools/princeton/s15/"
    };

		String[] seasonNames = new String[] {
			"Fall 19",
			"Spring 19",
			"Fall 18",
			"Spring 18",
			"Fall 17",
			"Spring 17",
			"Fall 16",
			"Spring 16",
			"Fall 15",
			"Spring 15"
		};

		StringBuilder sb = new StringBuilder();

    // The webpage
    Document doc = null;

    // Season loop
    for (int s = 0; s < seasons.length; s++) {
      String season = seasons[s];

      // Grab page
      try {
        doc = Jsoup.connect(season).get();
      } catch (Exception e) {
        e.printStackTrace();
			}

      // Grab table
      Element table = doc.getElementById("history");
      Elements rows = table.getElementsByTag("tr");

      // Row loop
			boolean first = true;
      for (Element row : rows) {
				if (first) {
					first = false;
					continue;
				}
				sb.append(seasonNames[s]);
        Elements tds = row.getElementsByTag("TD");
        // Column loop
        for (int i = 0; i < tds.size(); i++) {
					// Fill CSV
          String item = tds.get(i).text();
					if (i == 6) {
						 String[] places = item.split("/");
						 sb.append("@" + places[0] + "@" + places[1]);
					}
					else {
						sb.append("@" + item);
					}
        } // End Column Loop
        sb.append("\n");
      } // End Row Loop
    } // End Season Loop
		System.out.println(sb.toString());
	}
}
