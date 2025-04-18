package com.investmentplanner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stocks")
class StockPlanController {

    private List<StockPlan> getAllPlans() {
        return Arrays.asList(
            new StockPlan("TCS", "IT", 5000, 1.2, 60, "Low", "Balanced"),
            new StockPlan("ITC", "FMCG", 5000, 3.3, 165, "Low", "Dividend"),
            new StockPlan("SBI", "Banking", 5000, 2.0, 100, "Medium", "Balanced"),
            new StockPlan("NTPC", "Power", 5000, 2.3, 115, "Low", "Dividend"),
            new StockPlan("GAIL", "Oil & Gas", 5000, 3.6, 180, "Medium", "Dividend"),
            new StockPlan("M&M", "Auto", 5000, 0.8, 40, "Medium", "Growth"),
            new StockPlan("L&T", "Capital Goods", 5000, 0.9, 45, "Medium", "Growth"),
            new StockPlan("Sun Pharma", "Pharma", 5000, 0.9, 45, "Medium", "Balanced"),
            new StockPlan("Tata Steel", "Metals", 5000, 2.6, 130, "High", "Growth"),
            new StockPlan("Bajaj Finance", "Financial Services", 5000, 0.4, 20, "High", "Growth")
        );
    }

    @GetMapping("/recommendation")
    public List<StockPlan> getRecommendedPlan(
        @RequestParam(required = false) String risk,
        @RequestParam(required = false) String portfolioType,
        @RequestParam(required = false, defaultValue = "50000") int totalInvestment
    ) {

        List<StockPlan> filtered = new ArrayList<>();
        for (StockPlan plan : getAllPlans()) {
            if ((risk == null || risk.isEmpty() || plan.getRiskLevel().equalsIgnoreCase(risk)) &&
                (portfolioType == null || portfolioType.isEmpty() || plan.getPortfolioType().equalsIgnoreCase(portfolioType))) {
                filtered.add(plan);
            }
        }

        if (filtered.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, List<StockPlan>> sectorMap = filtered.stream().collect(Collectors.groupingBy(StockPlan::getSector));
        List<StockPlan> onePerSector = sectorMap.values().stream()
            .map(list -> list.stream().findFirst().orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        if (onePerSector.isEmpty()) {
            return Collections.emptyList(); // avoid division by zero
        }

        int perStockAmount = totalInvestment / onePerSector.size();
        onePerSector.forEach(plan -> plan.setMonthlyAmount(perStockAmount));

        return onePerSector;
    }
}

@Controller
class UIController {

    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("niftyWidget", "<iframe src=\"https://s.tradingview.com/widgetembed/?frameElementId=tradingview_12345&symbol=NSE:NIFTY&interval=D&hidesidetoolbar=1&symboledit=1&saveimage=1&toolbarbg=f1f3f6&studies=[]&theme=light&style=1&timezone=Asia/Kolkata&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=localhost&utm_medium=widget&utm_campaign=chart&utm_term=NSE:NIFTY\" width=\"100%\" height=\"400\" frameborder=\"0\" allowtransparency=\"true\" scrolling=\"no\"></iframe>");
        model.addAttribute("sensexWidget", "<iframe src=\"https://s.tradingview.com/widgetembed/?frameElementId=tradingview_54321&symbol=BSE:SENSEX&interval=D&hidesidetoolbar=1&symboledit=1&saveimage=1&toolbarbg=f1f3f6&studies=[]&theme=light&style=1&timezone=Asia/Kolkata&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=localhost&utm_medium=widget&utm_campaign=chart&utm_term=BSE:SENSEX\" width=\"100%\" height=\"400\" frameborder=\"0\" allowtransparency=\"true\" scrolling=\"no\"></iframe>");
        return "index";
    }

    @GetMapping("/investment-plan")
    public String getInvestmentPlanPage(
        @RequestParam(required = false) String risk,
        @RequestParam(required = false) String portfolioType,
        @RequestParam(required = false, defaultValue = "35") int age,
        @RequestParam(required = false, defaultValue = "50000") int amount,
        Model model
    ) {
        List<StockPlan> plans = new StockPlanController().getRecommendedPlan(risk, portfolioType, amount);
        model.addAttribute("plans", plans);
        model.addAttribute("age", age);
        model.addAttribute("amount", amount);
        return "investmentPlan";
    }
}