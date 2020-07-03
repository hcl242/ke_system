package com.hbfw.kesystem.comments;

import com.hbfw.kesystem.bean.*;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.ssl.SSLContexts;
import org.jsoup.Jsoup;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class SeleniumUtils {

    private static DefaultBasePath BASE_PATH;

    //小方块距离左边界距离，对应到原图的距离
    private static int START_DISTANCE = (22 + 16) * 2;

    public static SeleniumResult runSelenium(DefaultBasePath defaultBase, LoginClient client){
       return runSelenium(defaultBase,client.getUsername(),client.getPassword(),client.getVersion());
    }

    public static SeleniumResult runSelenium(DefaultBasePath defaultBase,String username, String password, int version){
        BASE_PATH = defaultBase;
//		System.out.println(getBkn("000400005ace843fcaf041c2e812fc62eb5ce7f0b52a46f594066be9049ac4afc8844a7a8ab6c3528bbb62bb"));
        if(version<70) version = 70;
        System.setProperty("webdriver.chrome.driver", BASE_PATH.getDriverPath()+"/chromedriver/"+version+"/chromedriver.exe");// chromedriver服务地址
//        System.setProperty("webdriver.chrome.bin", BASE_PATH.getDriverPath()+"/chromedriver/"+version+"/chromedriver.exe");// chromedriver服务地址
        ChromeOptions option = new ChromeOptions();
        option.setExperimentalOption("debuggerAddress", "127.0.0.1:"+BASE_PATH.getWebDriverPort());
        option.setBinary(BASE_PATH.getBinaryExeName());
        System.out.println(BASE_PATH.getBinaryExeName()+"---"+BASE_PATH.getWebDriverPort());
        WebDriver driver = new ChromeDriver(option); // 新建一个WebDriver 的对象，但是new 的是谷歌的驱动
//		WebDriver driver = new ChromeDriver();
        String url = "https://ke.qq.com/user/index/index.html";
        driver.get(url); // 打开指定的网站
//      driver.navigate().to("http://www.baidu.com");
//      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        if(existBy(driver, By.cssSelector("body > div.ptlogin-wrap > div > div.enter-content > div.content-btns > a.js-btns-enter.btns-enter.btns-enter-qq")))
        driver.findElement(By.cssSelector("body > div.ptlogin-wrap > div > div.enter-content > div.content-btns > a.js-btns-enter.btns-enter.btns-enter-qq")).click();;
        if(existBy(driver,By.className("mod-tab__content-tips"))){
            String js = "var div = document.getElementsByClassName('mod-tab__content-tips')[0];" +
                    "div.innerText = '当前正在进行自动登录及破解验证...请耐心等待，不要操作!';" +
                    "div.style = 'color:red;font-size:18px';";
            ((JavascriptExecutor) driver).executeScript(js);
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.switchTo().frame("login_frame_qq");
        driver.findElement(By.xpath("//*[@id=\"switcher_plogin\"]")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.switchTo().defaultContent();//回到最外层框架
        driver.switchTo().frame("login_frame_qq");//进入定位元素的框架
        doLogin(driver,username,password);
        StringBuffer sbf = new StringBuffer();
        long bkn = 0;
        Set<Cookie> cookies = driver.manage().getCookies();
        System.out.println("------------------------------------------------------");
        for(Cookie cookie:cookies) {
            if(cookie.getName().equals("p_lskey")) {
                bkn = getBkn(cookie.getValue());
            }
            sbf.append(cookie.getName()).append("=").append(cookie.getValue()).append("; ");
        }
        System.out.println("bkn:  "+bkn);
        System.out.println("cookie:  "+sbf);
        return new SeleniumResult(new LoginToken(bkn+"",sbf.toString()),driver);
    }
    private static long getBkn(String skey)
    {
        int hash = 5381;
        for (int i = 0, len = skey.length(); i < len; ++i)
        {
            hash += (hash << 5) + (int)skey.charAt(i);
        }
        return hash & 2147483647;
    }
    private static boolean existBy(WebDriver driver,By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean doLogin(WebDriver driver,String username,String password) {
        driver.findElement(By.xpath("//*[@id=\"u\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"u\"]")).sendKeys(username);
        driver.findElement(By.xpath("//*[@id=\"p\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"p\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"login_button\"]")).click();
        //tcaptcha_iframe
        boolean flag = true;
        try {
            driver.switchTo().frame("tcaptcha_iframe");
        } catch (Exception e) {
            // TODO: handle exception
            flag = false;
        }
        System.out.println("是否存在验证:\t"+(flag?"是":"否"));
        while(flag) {
            try {
                //
                System.out.println("move start...");
                driver.findElement(By.xpath("//*[@id=\"e_reload\"]")).click();
                doMove(driver);
                flag = existBy(driver, By.id("tcaptcha_drag_thumb"));
                System.out.println(flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return !flag;
    }
    private static boolean doMove(WebDriver driver) throws Exception {
        try {
            ActionsImpl actions = new ActionsImpl(driver);
            String originalUrl = Jsoup.parse(driver.getPageSource()).select("[id=slideBg]").first().attr("src");//slideBg
            System.out.println(originalUrl);
            downloadOriginalImg(originalUrl, driver.manage().getCookies());
            int bgWrapWidth = driver.findElement(By.id("slideBgWrap")).getSize().getWidth();//slideBgWrap
            int distance = calcMoveDistance(bgWrapWidth);
            List<MoveEntity> list = getMoveEntity(distance);
            WebElement element = driver.findElement(By.id("tcaptcha_drag_button"));//tcaptcha_drag_thumb
            actions.clickAndHold(element).perform();
            int d = 0;
            for (MoveEntity moveEntity : list) {
                actions.moveByOffset(moveEntity.getX(),moveEntity.getY()).perform();
                System.out.println("破解验证中------------------------------------向右总共移动了:" + (d = d + moveEntity.getX()));
                Thread.sleep(moveEntity.getSleepTime());
            }
            actions.release(element).perform();
            Thread.sleep(2 * 1000);
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void downloadOriginalImg(String originalUrl, Set<Cookie> cookieSet) throws IOException {
        CookieStore cookieStore = new BasicCookieStore();
        cookieSet.forEach( c -> {
            BasicClientCookie cookie = new BasicClientCookie(c.getName(), c.getValue());
            cookie.setPath(c.getPath());
            cookie.setDomain(c.getDomain());
            cookie.setExpiryDate(c.getExpiry());
            cookie.setSecure(true);
            cookieStore.addCookie(cookie);
        });
        InputStream is = null;
        try {
            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(KeyStore.getInstance(KeyStore.getDefaultType())
                            , (chain, authType) -> true).build();
            Registry<ConnectionSocketFactory> socketFactoryRegistry =
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.INSTANCE)
                            .register("https", new SSLConnectionSocketFactory(sslContext))
                            .build();
            is = HttpClients.custom()
//                    .setProxy(new HttpHost("127.0.0.1", 8888))
                    .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36")
                    .setDefaultCookieStore(cookieStore)
                    .setConnectionManager(new PoolingHttpClientConnectionManager(socketFactoryRegistry))
                    .build()
                    .execute(new HttpGet(originalUrl))
                    .getEntity().getContent();
            FileUtils.copyInputStreamToFile(is, new File(BASE_PATH.getCrawlerBasePath() + "/tencent-original" + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算小方块需要移动的距离
     * @param
     * @param bgWrapWidth 背景图片div对应的width
     * @return
     * @throws IOException
     */
    public static int calcMoveDistance(float bgWrapWidth) throws IOException {
        BufferedImage fullBI = ImageIO.read(new File(BASE_PATH.getCrawlerBasePath() + "/tencent-original" + ".png"));
        for(int w = 340 ; w < fullBI.getWidth() - 18; w++){
            int whiteLineLen = 0;
            for (int h = 0; h < fullBI.getHeight(); h++){
                int[] fullRgb = new int[3];
                fullRgb[0] = (fullBI.getRGB(w, h)  & 0xff0000) >> 16;
                fullRgb[1] = (fullBI.getRGB(w, h)  & 0xff00) >> 8;
                fullRgb[2] = (fullBI.getRGB(w, h)  & 0xff);
                if (isBlack28(fullBI, w, h) && isWhite(fullBI, w, h)) {
                    whiteLineLen++;
                } else {
//                    whiteLineLen = 0;
                    continue;
                }
                if (whiteLineLen >= 50){
                    System.out.println("找到缺口成功，实际缺口位置x：" + w);
                    System.out.println("应该移动距离：" + (w - START_DISTANCE) / (fullBI.getWidth() / bgWrapWidth));
                    return (int) ((w - START_DISTANCE) / (fullBI.getWidth() / bgWrapWidth));
                }
            }

        }
        throw new RuntimeException("计算缺口位置失败");
    }
    /**
     * 当前点的后28个是不是黑色
     *
     * @return 后28个中有80%是黑色返回true, 否则返回false
     */
    private static boolean isBlack28(BufferedImage fullBI, int w, int h) {
        int[] fullRgb = new int[3];
        double blackNum = 0;
        int num = Math.min(fullBI.getWidth() - w, 28);
        for (int i = 0; i < num; i++) {
            fullRgb[0] = (fullBI.getRGB(w + i, h) & 0xff0000) >> 16;
            fullRgb[1] = (fullBI.getRGB(w + i, h) & 0xff00) >> 8;
            fullRgb[2] = (fullBI.getRGB(w + i, h) & 0xff);
            if (isBlack(fullRgb)) {
                blackNum = blackNum + 1;
            }
        }

        return blackNum / num > 0.8;
    }

    /**
     * 当前点是不是白色
     *
     * @param fullBI
     * @param w
     * @param h
     * @return
     */
    private static boolean isWhite(BufferedImage fullBI, int w, int h) {
        int[] fullRgb = new int[3];
        fullRgb[0] = (fullBI.getRGB(w, h) & 0xff0000) >> 16;
        fullRgb[1] = (fullBI.getRGB(w, h) & 0xff00) >> 8;
        fullRgb[2] = (fullBI.getRGB(w, h) & 0xff);

        return isWhite(fullRgb);
    }

    private static boolean isWhite(int[] fullRgb) {
        return (Math.abs(fullRgb[0] - 0xff) + Math.abs(fullRgb[1] - 0xff) + Math.abs(fullRgb[2] - 0xff)) < 125;
    }

    private static boolean isBlack(int[] fullRgb) {
        return fullRgb[0] * 0.3 + fullRgb[1] * 0.6 + fullRgb[2] * 0.1 <= 125;
    }

    /**
     * 默认移动算法
     * @param distance
     * @return
     */
    public static List<MoveEntity> getMoveEntity(int distance){
        List<MoveEntity> list = new ArrayList<>();
        for (int i = 0 ;i < distance; i++){

            MoveEntity moveEntity = new MoveEntity();
            moveEntity.setX(1);
            moveEntity.setY(0);
            moveEntity.setSleepTime(0);
            list.add(moveEntity);
        }
        return list;
    }

    /**
     * 新增的一种移动算法
     * @param distance
     * @return
     */
    public static List<MoveEntity> getMoveEntity1(int distance){
        List<MoveEntity> list = new ArrayList<>();
        for (int i = 0 ;i < distance / 5; i++){
            MoveEntity moveEntity = new MoveEntity();
            moveEntity.setX(5);
            moveEntity.setY(ThreadLocalRandom.current().nextBoolean() ? 10 : -10);
            moveEntity.setSleepTime(10);
            list.add(moveEntity);
        }

        MoveEntity moveEntity = new MoveEntity();
        moveEntity.setX(distance % 5);
        moveEntity.setY(0);
        moveEntity.setSleepTime(10);
        list.add(moveEntity);
        return list;
    }
}
