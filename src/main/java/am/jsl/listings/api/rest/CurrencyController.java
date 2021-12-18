package am.jsl.listings.api.rest;

import am.jsl.listings.domain.Currency;
import am.jsl.listings.dto.Pair;
import am.jsl.listings.service.currency.CurrencyService;
import am.jsl.listings.util.TextUtils;
import am.jsl.listings.api.rest.BaseController;
import am.jsl.listings.web.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The CurrencyController defines methods for viewing currencies.
 *
 * @author hamlet
 */
@RestController
@RequestMapping(value = "/api/currencies")
@Lazy
public class CurrencyController extends BaseController {

    /**
     * The CurrencyService service
     */
    @Autowired
    private transient CurrencyService currencyService;

    /**
     * Returns currencies.
     *
     * @return the response
     */
    @GetMapping()
    public Response<List<Currency>> list() {
        List<Currency> currencies = currencyService.list();
        return Response.ok(currencies);
    }

    /**
     * Converts the given amount from source currency to target currency.
     *
     * @param amount the amount
     * @param from   the source currency
     * @param to     the target currency
     * @return the json containing pair of rate, converted amount
     */
    @RequestMapping(value = {"/convert"}, method = RequestMethod.POST)
    public @ResponseBody
    JsonResponse convert(@RequestParam(value = "amount") double amount,
                         @RequestParam(value = "from") String from,
                         @RequestParam(value = "to") String to) {
        JsonResponse response = new JsonResponse();

        if (!TextUtils.isEmpty(from) && !TextUtils.isEmpty(to)) {
            double rate = currencyService.getRate(from, to);
            amount = amount * rate;

            Pair<Double, Double> rateAmount = new Pair<>(rate, amount);
            response.setResult(rateAmount);
        }

        return response;
    }

    /**
     * Converts the given amount with the given rate.
     *
     * @param amount the amount
     * @param rate   the rate
     * @return the converted amount
     */
    @RequestMapping(value = {"/convertWithRate"}, method = RequestMethod.POST)
    public @ResponseBody
    JsonResponse convertWithRate(@RequestParam(value = "amount") double amount,
                                 @RequestParam(value = "rate") double rate) {
        JsonResponse response = new JsonResponse();
        response.setResult(amount * rate);
        return response;
    }
}
