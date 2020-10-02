package com.devparadigam.agrade.utils;


/**
 * Created by Anuj Kumawat (Dev paradigm)
 */
public enum AppEnvironment {

    SANDBOX {
        @Override
        public String merchant_Key() {
            return "P54j15ds";
        }

        @Override
        public String merchant_ID() {
            return "6925744";
        }

        @Override
        public String furl() {
            return "https://www.payumoney.com/mobileapp/payumoney/failure.php";
        }

        @Override
        public String surl() {
            return "https://www.payumoney.com/mobileapp/payumoney/success.php";
        }

        @Override
        public String salt() {
            return "OjWoiHE7vg";
        }
        //header wd4za1N7wpWAvwGbV9xXlO6fxOhrRIzD8wvpkaZfNOs=
        // agradeclasses@gmail.com  // 7793031540

        @Override
        public boolean debug() {
            return true;
        }
    },
    PRODUCTION {
        @Override
        public String merchant_Key() {
            return "P54j15ds";
        }
        @Override
        public String merchant_ID() {
            return "6925744";
        }
        @Override
        public String furl() {
            return "https://www.payumoney.com/mobileapp/payumoney/failure.php";
        }

        @Override
        public String surl() {
            return "https://www.payumoney.com/mobileapp/payumoney/success.php";
        }

        @Override
        public String salt() {
            return "OjWoiHE7vg";
        }
        // header wd4za1N7wpWAvwGbV9xXlO6fxOhrRIzD8wvpkaZfNOs=

        @Override
        public boolean debug() {
            return false;
        }
    };

    public abstract String merchant_Key();

    public abstract String merchant_ID();

    public abstract String furl();

    public abstract String surl();

    public abstract String salt();

    public abstract boolean debug();


}
