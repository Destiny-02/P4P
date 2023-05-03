package payroll;
class Income {
    private float round2dp(float number) {
        return Math.round(number*100.0f)/100.0f;
    }
    float calculateGross(String jobType, float rate, float hours) {
        switch (jobType) {
            case "Hourly":
                if (hours<=40.0f) {
                    return round2dp(rate * hours);
                }
                if (hours<=43.0f) {
                    return round2dp((rate*40.0f)+((hours-40.0f)*rate*1.5f));
                }
                return round2dp((rate*40.0f)+(3*rate*1.5f)+((hours-43.0f)*rate*2.0f));
            case "Salaried":
                return round2dp(rate/52);
        }
        return 0;
    }
    float calculateNetPay(float gross, float tax, float deductions) {
        return round2dp(gross - tax - deductions);
    }
    float newYTD(float gross, float ytd) {
        return round2dp(gross + ytd);
    }
    float calculatePAYE(float gross) {
        gross *= 52;
        if (gross<14000) {
            return round2dp((gross*0.105f)/52.0f);
        }
        float sum = 14000*0.105f;
        if (gross<48000) {
            return round2dp((sum+(gross-14000)*0.175f)/52.0f);
        }
        sum += 34000*0.175f;
        if (gross<70000) {
            return round2dp((sum+(gross-48000)*0.3f)/52.0f);
        }
        sum += 22000*0.3f;
        return round2dp((sum+(gross-70000)*0.33f)/52.0f);
    }
}
