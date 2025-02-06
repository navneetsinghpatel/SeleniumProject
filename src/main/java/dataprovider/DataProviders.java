package dataprovider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.LoginData;
import org.testng.annotations.DataProvider;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class DataProviders {

    @DataProvider(name = "loginDataProvider")
    public static Object[][] provideLoginData() throws IOException {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<LoginData>>(){}.getType();
        List<LoginData> loginDataList = gson.fromJson(new FileReader("src/test/resources/data/logintestdata.json"), listType);

        Object[][] data = new Object[loginDataList.size()][3];
        for (int i = 0; i < loginDataList.size(); i++) {
            data[i][0] = loginDataList.get(i).getUsername();
            data[i][1] = loginDataList.get(i).getPassword();
            data[i][2] = loginDataList.get(i).getExpectedResult();
        }
        return data;
    }
}
