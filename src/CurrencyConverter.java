import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyConverter {

    //API KEY
    private static final String API_KEY = "35d2d715780ccd91b9147d32";

    // URL base da Exchange Rate API
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    private static final String[] CURRENCY_CODES = {
            "USD", // 1
            "EUR", // 2
            "GBP", // 3
            "JPY", // 4
            "BRL", // 5
            "CAD"  // 6
    };

    private static final String[] CURRENCY_NAMES = {
            "Dólar Americano",
            "Euro",
            "Libra Esterlina",
            "Iene Japonês",
            "Real Brasileiro",
            "Dólar Canadense"
    };

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("====================================");
        System.out.println("      Conversor de Moedas (API)     ");
        System.out.println("====================================\n");

        boolean continuar = true;

        while (continuar) {
            try {
                System.out.println("Escolha a moeda de ORIGEM:");
                int origemIndex = escolherMoeda(scanner);

                System.out.println("\nEscolha a moeda de DESTINO:");
                int destinoIndex = escolherMoeda(scanner);

                String moedaOrigem = CURRENCY_CODES[origemIndex];
                String moedaDestino = CURRENCY_CODES[destinoIndex];

                if (moedaOrigem.equals(moedaDestino)) {
                    System.out.println("\nOrigem e destino são iguais, não há conversão.");
                } else {
                    System.out.print("\nDigite o valor a ser convertido em " +
                            moedaOrigem + " (" + CURRENCY_NAMES[origemIndex] + "): ");
                    double valor = lerValor(scanner);

                    double convertido = converterMoeda(moedaOrigem, moedaDestino, valor);

                    System.out.printf("\nResultado: %.2f %s (%s) = %.2f %s (%s)%n%n",
                            valor, moedaOrigem, CURRENCY_NAMES[origemIndex],
                            convertido, moedaDestino, CURRENCY_NAMES[destinoIndex]);
                }

            } catch (Exception e) {
                System.out.println("\nOcorreu um erro: " + e.getMessage());
                System.out.println("Tenta novamente ou verifica tua conexão com a internet / API KEY.\n");
            }

            System.out.println("Deseja fazer outra conversão? (s/n)");
            System.out.print("-> ");
            String resp = scanner.next().trim().toLowerCase();
            System.out.println();
            if (!resp.equals("s") && !resp.equals("sim")) {
                continuar = false;
            }
        }

        System.out.println("Obrigado por usar o conversor de moedas! :)");
        scanner.close();
    }

    // Mostra o menu de moedas e retorna o índice escolhido (0 a 5)
    private static int escolherMoeda(Scanner scanner) {
        for (int i = 0; i < CURRENCY_CODES.length; i++) {
            System.out.printf("%d - %s (%s)%n", (i + 1), CURRENCY_NAMES[i], CURRENCY_CODES[i]);
        }

        int opcao;
        while (true) {
            System.out.print("Digite o número da moeda: ");
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                if (opcao >= 1 && opcao <= CURRENCY_CODES.length) {
                    break;
                } else {
                    System.out.println("Opção inválida. Escolha um número entre 1 e " + CURRENCY_CODES.length + ".");
                }
            } else {
                System.out.println("Entrada inválida. Digite apenas números.");
                scanner.next(); // limpa entrada errada
            }
        }

        // converter pra índice de array (0 a 5)
        return opcao - 1;
    }

    // Lê um valor double com validação simples
    private static double lerValor(Scanner scanner) {
        double valor;
        while (true) {
            if (scanner.hasNextDouble()) {
                valor = scanner.nextDouble();
                if (valor >= 0) {
                    break;
                } else {
                    System.out.print("Digite um valor positivo: ");
                }
            } else {
                System.out.print("Entrada inválida. Digite um número: ");
                scanner.next(); // limpa entrada errada
            }
        }
        return valor;
    }


    private static double converterMoeda(String from, String to, double amount)
            throws IOException, InterruptedException {

        String url = BASE_URL + API_KEY + "/pair/" + from + "/" + to + "/" + amount;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP " + response.statusCode() + " ao chamar a API.");
        }

        String body = response.body();

        // Verifica se a API respondeu sucesso
        if (!body.contains("\"result\":\"success\"")) {
            throw new RuntimeException("API retornou erro: " + body);
        }

        // Procura o campo "conversion_result" com regex simples
        Pattern pattern = Pattern.compile("\"conversion_result\"\\s*:\\s*([0-9.]+)");
        Matcher matcher = pattern.matcher(body);

        if (matcher.find()) {
            String valorStr = matcher.group(1);
            return Double.parseDouble(valorStr);
        } else {
            throw new RuntimeException("Não foi possível ler o campo conversion_result da resposta.");
        }
    }
}
