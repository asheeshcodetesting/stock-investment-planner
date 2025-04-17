package com.investmentplanner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/api/stocks")
class StockPlanController {

    @GetMapping("/recommendation")
    public List<StockPlan> getRecommendedPlan() {
        return Arrays.asList(
            new StockPlan("TCS", "IT", 5000, 1.2, 60),
            new StockPlan("ITC", "FMCG", 5000, 3.3, 165),
            new StockPlan("SBI", "Banking", 5000, 2.0, 100),
            new StockPlan("NTPC", "Power", 5000, 2.3, 115),
            new StockPlan("GAIL", "Oil & Gas", 5000, 3.6, 180),
            new StockPlan("M&M", "Auto", 5000, 0.8, 40),
            new StockPlan("L&T", "Capital Goods", 5000, 0.9, 45),
            new StockPlan("Sun Pharma", "Pharma", 5000, 0.9, 45),
            new StockPlan("Tata Steel", "Metals", 5000, 2.6, 130),
            new StockPlan("Bajaj Finance", "Financial Services", 5000, 0.4, 20)
        );
    }
}

@Controller
class UIController {

    @GetMapping("/investment-plan")
    public String getInvestmentPlanPage(Model model) {
        List<StockPlan> plans = new StockPlanController().getRecommendedPlan();
        model.addAttribute("plans", plans);
        return "investmentPlan";
    }
}