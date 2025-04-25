package com.rzfk.common.jsonp;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Flutter {

    public static List<Map<String, String>> getFlutterDemos(String i) throws Exception {
        List<Map<String, String>> res = new ArrayList<>();
        String url = "https://vuejsexamples.com/page/" + i + "/";
        url = URLDecoder.decode(url, String.valueOf(StandardCharsets.UTF_8));
        //模拟浏览器访问，防止阻止跳到登录页面
        Connection mozilla = Jsoup.connect(url).userAgent("Mozilla");
        Document document = mozilla.get();
        Element element = document.body();
        Element j_goodsList = element.getElementsByTag("main").get(0);
        Elements lis = j_goodsList.getElementsByClass("post-column col-xl-4 col-md-6");
        for (Element li : lis) {
            Map<String, String> map = new HashMap<>();
            String tag = li.getElementsByClass("post-tag").eq(0).text();
            map.put("tag", tag);
            System.out.println("tag: " + tag);
            String title = li.getElementsByClass("post-title-link").eq(0).text();
            map.put("title", title);
            System.out.println("title: " + title);
            String title1 = li.getElementsByClass("post-excerpt").eq(0).text();
            map.put("title1", title1);
            System.out.println("title1: " + title1);
            String time = li.getElementsByClass("post-card-tags").eq(0).text();
            map.put("time", time);
            System.out.println("time: " + time);
            String url1 = li.getElementsByTag("a").eq(0).attr("href");
            map.put("url1", url1);
            System.out.println("url: " + url1);
            String img = li.getElementsByTag("img").eq(0).attr("data-srcset");
            map.put("img", img);
            System.out.println("img: " + img);
            System.out.println("——————————————————————————————————————————————");
            res.add(map);
            System.out.println("******************************** " + i + " **************************");
        }
        return res;
    }

    public static Map<String, String> getDetail(String url) {
        Map<String, String> map = new HashMap<>();
        try {
            url = URLDecoder.decode(url, String.valueOf(StandardCharsets.UTF_8));
            //模拟浏览器访问，防止阻止跳到登录页面
            Connection mozilla = Jsoup.connect(url).userAgent("Mozilla");
            Document document = mozilla.get();
            Element element = document.body();
            Element j_goodsList = element.getElementsByTag("article").get(0);
            Elements li = j_goodsList.getElementsByClass("post-content u-text-format");
            System.out.println(li.toString());
            map.put("content", li.toString());
            if (j_goodsList.getElementsByClass("github-view").size() != 0) {
                String github = j_goodsList.getElementsByClass("github-view").get(0).attr("href");
                System.out.println(github);
                map.put("git", github);
            } else {
                Elements as = li.get(0).getElementsByTag("a");
                if (as.size() != 0) {
                    String github = as.get(as.size() - 1).text();
                    System.out.println(github);
                    map.put("git", github);
                }
            }
        }catch (Exception e){

        }

        return map;
    }

//    public static void main(String[] args) throws Exception {
//        String url = "https://www.bookstack.cn/cate";
//        url = URLDecoder.decode(url, String.valueOf(StandardCharsets.UTF_8));
//        //模拟浏览器访问，防止阻止跳到登录页面
//        Connection mozilla = Jsoup.connect(url).userAgent("Mozilla");
//        Document document = mozilla.get();
//        Element element = document.body();
//        Element all = element.getElementsByClass("col-sm-10 col-xs-12 col-md-10 category-detail").get(0);
//        Elements category = all.getElementsByClass("panel panel-default");
//        for (int i = 0; i < category.size(); i++) {
//            Element info = category.get(i).getElementsByClass("panel-heading").get(0);
//            String name = info.getElementsByTag("a").eq(0).attr("title");
//            String href = info.getElementsByTag("a").eq(0).attr("href");
//            System.out.println("name: " + name + " --- href: " + href);
//            Elements items = category.get(i).getElementsByClass("panel-body").get(0).getElementsByClass("col-xs-12 col-sm-6 col-md-4");
//            for (int j = 0; j < items.size(); j++) {
//                String name1 = items.get(j).getElementsByClass("pull-left").eq(0).attr("title");
//                String href1 = items.get(j).getElementsByClass("pull-left").eq(0).attr("href");
//                String desc = items.get(j).getElementsByClass("help-block").eq(0).text();
//                String img = items.get(j).getElementsByTag("img").eq(0).attr("src");
//                System.out.println("<<<<<----- name: " + name1 + " --- href: " + href1 + " --- img: " + img + " --- desc: " + desc);
//            }
//        }
//    }

//    public static void main(String[] args) throws Exception {
//        for (int a = 1; a < 100; a++) {
//            String url = "https://www.bookstack.cn/explore?page=" + a + "&tab=popular&cid=108&lang=";
//            url = URLDecoder.decode(url, String.valueOf(StandardCharsets.UTF_8));
//            //模拟浏览器访问，防止阻止跳到登录页面
//            Connection mozilla = Jsoup.connect(url).userAgent("Mozilla");
//            Document document = mozilla.get();
//            Element element = document.body();
//            Element all = element.getElementsByClass("manual-list").get(0);
//            Elements books = all.getElementsByClass("col-xs-3 col-sm-3 col-md-2");
//            if (!books.isEmpty()) {
//                for (int i = 0; i < books.size(); i++) {
//                    Element info = books.get(i).getElementsByClass("manual-item-standard").get(0);
//                    String name = info.getElementsByTag("img").eq(0).attr("alt");
//                    String href = info.getElementsByTag("a").eq(0).attr("href");
//                    String img = info.getElementsByTag("img").eq(0).attr("src");
//
//                    String url1 = "https://www.bookstack.cn" + href;
//                    url1 = URLDecoder.decode(url1, String.valueOf(StandardCharsets.UTF_8));
//                    //模拟浏览器访问，防止阻止跳到登录页面
//                    Connection mozilla1 = Jsoup.connect(url1).userAgent("Mozilla");
//                    Document document1 = mozilla1.get();
//                    Element element1 = document1.body();
//                    Elements tags = element1.getElementsByClass("bookstack-labels").get(0).getElementsByTag("a");
//                    String tagStr = "";
//                    if (!tags.isEmpty()) {
//                        for (int j = 0; j < tags.size(); j++) {
//                            String tag = tags.get(j).attr("title");
//                            tagStr += tag + ",";
//                        }
//                    }
//                    String desc = element1.getElementsByClass("bookstack-description hidden-xs").eq(0).text();
//                    String readHref = element1.getElementsByClass("btn btn-info btn-lg").eq(0).attr("href");
//
//
//                    System.out.println("name: " + name + " --- href: " + href + " --- img: " + img + " --- tag: " + tagStr);
//                    System.out.println("desc: " + desc);
//                    System.out.println("readHref: " + readHref);
////                    Elements items = books.get(i).getElementsByClass("panel-body").get(0).getElementsByClass("col-xs-12 col-sm-6 col-md-4");
////                    for (int j = 0; j < items.size(); j++) {
////                        String name1 = items.get(j).getElementsByClass("pull-left").eq(0).attr("title");
////                        String href1 = items.get(j).getElementsByClass("pull-left").eq(0).attr("href");
////                        String desc = items.get(j).getElementsByClass("help-block").eq(0).text();
////                        String img = items.get(j).getElementsByTag("img").eq(0).attr("src");
////                        System.out.println("<<<<<----- name: " + name1 + " --- href: " + href1 + " --- img: " + img + " --- desc: " + desc);
////                    }
//                }
//            } else {
//                return;
//            }
//
//        }
//
//    }

//    public static void main(String[] args) throws Exception {
//        String url = "https://www.bookstack.cn/read/es6-3rd/sidebar.md";
//        url = URLDecoder.decode(url, String.valueOf(StandardCharsets.UTF_8));
//        //模拟浏览器访问，防止阻止跳到登录页面
//        Connection mozilla = Jsoup.connect(url).userAgent("Mozilla");
//        Document document = mozilla.get();
//        Element element = document.body();
//        Element menus = element.getElementsByClass("article-menu-detail collapse-menu").get(0);
//        Elements menu = menus.getElementsByTag("ul").get(0).children();
//        for (int i = 0; i < menu.size(); i++) {
//            Element one = menu.get(i);
//            String href1 = one.getElementsByTag("a").eq(0).attr("href");
//            String name1 = one.getElementsByTag("a").eq(0).text();
//            System.out.println("----1-----name: " + name1 + "   -----1----href: " + href1);
//            Elements twos = one.getElementsByTag("ul");
//            if(!twos.isEmpty()){
//                for (int j = 0; j < twos.get(0).children().size(); j++) {
//                    Element two = twos.get(0).children().get(j);
//                    String href2 = two.getElementsByTag("a").eq(0).attr("href");
//                    String name2 = two.getElementsByTag("a").eq(0).text();
//                    System.out.println(">>>> >>>> ------2-----name: " + name2 + "   -----2----href: " + href2);
//                    Elements threes = two.getElementsByTag("ul");
//                    if(!threes.isEmpty()){
//                        for (int k = 0; k < threes.get(0).children().size(); k++) {
//                            Element three = threes.get(0).children().get(k);
//                            String href3 = three.getElementsByTag("a").eq(0).attr("href");
//                            String name3 = three.getElementsByTag("a").eq(0).text();
//                            System.out.println(">>>> >>>> >>>> ------3-----name: " + name3 + "   -----3----href: " + href3);
//                            Elements fours = three.getElementsByTag("ul");
//                            if(!fours.isEmpty()){
//                                for (int l = 0; l < fours.get(0).children().size(); l++) {
//                                    Element four = fours.get(0).children().get(l);
//                                    String href4 = four.getElementsByTag("a").eq(0).attr("href");
//                                    String name4 = four.getElementsByTag("a").eq(0).text();
//                                    System.out.println(">>>> >>>> >>>> >>>> ------4-----name: " + name4 + "   -----4----href: " + href4);
//                                    Elements fives = four.getElementsByTag("ul");
//                                    if(!fives.isEmpty()){
//                                        for (int m = 0; m < fives.get(0).children().size(); m++) {
//                                            Element five = fives.get(0).children().get(m);
//                                            String href5 = five.getElementsByTag("a").eq(0).attr("href");
//                                            String name5 = five.getElementsByTag("a").eq(0).text();
//                                            System.out.println(">>>> >>>> >>>> >>>> >>>> ------5-----name: " + name5 + "   -----5----href: " + href5);
//                                            Elements sixs = five.getElementsByTag("ul");
//                                            if(!sixs.isEmpty()){
//                                                for (int n = 0; n < sixs.get(0).children().size(); n++) {
//                                                    Element six = sixs.get(0).children().get(n);
//                                                    String href6 = six.getElementsByTag("a").eq(0).attr("href");
//                                                    String name6 = six.getElementsByTag("a").eq(0).text();
//                                                    System.out.println(">>>> >>>> >>>> >>>> >>>> >>>> ------6-----name: " + name6 + "   -----6----href: " + href6);
//                                                    Elements sevens = six.getElementsByTag("ul");
//                                                    if(!sevens.isEmpty()){
//                                                        for (int o = 0; o < sevens.get(0).children().size(); o++) {
//                                                            Element seven = sevens.get(0).children().get(o);
//                                                            String href7 = seven.getElementsByTag("a").eq(0).attr("href");
//                                                            String name7 = seven.getElementsByTag("a").eq(0).text();
//                                                            System.out.println(">>>> >>>> >>>> >>>> >>>> >>>> >>>> ------7-----name: " + name7 + "   -----7----href: " + href7);
//                                                            Elements eights = seven.getElementsByTag("ul");
//                                                            if(!eights.isEmpty()){
//                                                                for (int p = 0; p < eights.get(0).children().size(); p++) {
//                                                                    Element eight = eights.get(0).children().get(p);
//                                                                    String href8 = eight.getElementsByTag("a").eq(0).attr("href");
//                                                                    String name8 = eight.getElementsByTag("a").eq(0).text();
//                                                                    System.out.println(">>>> >>>> >>>> >>>> >>>> >>>> >>>> >>>> ------8-----name: " + name8 + "   -----8----href: " + href8);
//                                                                    Elements nines = eight.getElementsByTag("ul");
//                                                                    if(!nines.isEmpty()){
//                                                                        for (int q = 0; q < nines.get(0).children().size(); q++) {
//                                                                            Element nine = nines.get(0).children().get(q);
//                                                                            String href9 = nine.getElementsByTag("a").eq(0).attr("href");
//                                                                            String name9 = nine.getElementsByTag("a").eq(0).text();
//                                                                            System.out.println(">>>> >>>> >>>> >>>> >>>> >>>> >>>> >>>> >>>> ------9-----name: " + name9 + "   -----9----href: " + href9);
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

    public static void main(String[] args) throws Exception {
        String url = "https://www.bookstack.cn/read/note-of-interview/README.md";
        url = URLDecoder.decode(url, String.valueOf(StandardCharsets.UTF_8));
        //模拟浏览器访问，防止阻止跳到登录页面
        Connection mozilla = Jsoup.connect(url).userAgent("Mozilla");
        Document document = mozilla.get();
        Element element = document.body();
        Element j_goodsList = element.getElementsByTag("article").get(0);
        System.out.println(j_goodsList.toString());
    }


    public static Map<String, String> recursion() {
        Map<String, String> res = new HashMap<>();
        return res;
    }


    public static String getWebCode(String i) throws IOException {
        String url = "https://flutterawesome.com/page/" + i + "/";
//        String url = "https://flutterawesome.com/authenticator-app-for-storing-your-2fa-secrets/";
        url = URLDecoder.decode(url, String.valueOf(StandardCharsets.UTF_8));
        //模拟浏览器访问，防止阻止跳到登录页面
        Connection mozilla = Jsoup.connect(url).userAgent("Mozilla");
        Document document = mozilla.get();
        Element element = document.body();
        Element j_goodsList = element.getElementsByTag("main").get(0);
//        Elements li = j_goodsList.getElementsByClass("post-content u-text-format");
//        System.out.println(li.toString());
//        Elements as = li.get(0).getElementsByTag("a");
//        if(as.size()!=0){
//            String github = as.get(as.size()-1).text();
//            System.out.println(github);
//        }
        System.out.println(j_goodsList);
        return j_goodsList.toString();
    }
}
