import com.kavenegar.sdk.KavenegarApi;
import com.kavenegar.sdk.excepctions.HttpException;

/**
 * Created by farzad on 2/9/2018.
 */
public class Test {
    public static void main(String[] args) {
        try {
                String tel = "09137009097," +
                    "09109254673," +
                    "09032695916," +
                    "09010636064," +
                    "09379614885," +
                    "09380460859," +
                    "09108834427," +
                    "09364543320," +
                    "09388259595,";

            KavenegarApi api = new KavenegarApi("5635717141617A52534F636F49546D38454E647870773D3D");

                for (String s : tel.split(",")){

            api.send("10006006606600", s, "DAGALA شما یک بازی در حال انجام در لیگ شارژ دارید اطلاعات بیشتر :\r\n https://goo.gl/6SwRiJ ");

                }

        } catch (HttpException ex) { // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
            System.out.print("HttpException  : " + ex.getMessage());
        }
    }
}
