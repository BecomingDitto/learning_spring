package hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quote")
public class StockQuoteController {

    private final AtomicLong counter = new AtomicLong();
    private static final String template = "$%s";

    @GetMapping
    public StockQuote quote(@RequestParam(value="ticker", defaultValue="gddy") String ticker) {
        String quoteValue = "";
        try {
            URL url = new URL("https://api.iextrading.com/1.0/stock/" + ticker + "/price");
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;

            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            } else {
                System.out.println("Nope");
                throw new RuntimeException("Nope");
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String urlString = "";
            String current;
            while ((current = in.readLine()) != null) {
                urlString += current;
            }
            System.out.println(urlString);
            quoteValue = urlString;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new StockQuote(counter.incrementAndGet(),
                              String.format(template, quoteValue));

    }
}
