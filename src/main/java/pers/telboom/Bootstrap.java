package pers.telboom;

import pers.telboom.api.ApiLoader;
import pers.telboom.driver.DriverConfig;
import pers.telboom.driver.DriverType;
import pers.telboom.impl.BaiduiShanQiaoBoomImpl;
import pers.telboom.impl.IBoom;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Files;

public class Bootstrap {
    public static void main(String[] args) throws Exception {
        String driverPath= FileSystemView.getFileSystemView().getHomeDirectory().getPath() + "\\call\\driver"+"\\chromedriver.exe";
        DriverType driverType=DriverType.CHROME;
        DriverConfig driverConfig=new DriverConfig(driverType,driverPath);
        startBoom(driverConfig);
    }
    private static void startBoom(DriverConfig driverConfig)throws Exception {
        String telephone = "请问请问";
        File phonetxt=new File(FileSystemView.getFileSystemView().getHomeDirectory(),"\\call\\phone.txt");
        // 读取文件第一行
         telephone = Files.readAllLines(phonetxt.toPath()).get(0);
        BoomConfig boomConfig=new BoomConfig();
        boomConfig.setTelephone(telephone);


        File apiFile=new File(FileSystemView.getFileSystemView().getHomeDirectory(),"\\call\\api");

        ApiLoader apiLoader = new ApiLoader();
        apiLoader.init();
        apiLoader.load(apiFile);

        IBoom boom=new BaiduiShanQiaoBoomImpl(boomConfig)
                .init(driverConfig);
        boom.setStartApis(apiLoader.getApisByType(boom.getAccpetApiType()));
        boom.start("https://www.baidu.com");
    }
}
