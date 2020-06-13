package com.funong.funong.Data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.funong.funong.mapper.AreaDao;
import com.funong.funong.mapper.CityDao;
import com.funong.funong.mapper.ProvinceDao;
import com.funong.funong.plugin.GetImgUrl;
import com.funong.funong.pojo.Area;
import com.funong.funong.pojo.City;
import com.funong.funong.pojo.Civi;
import com.funong.funong.pojo.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

/**
 * @Author: cytern
 * @Date: 2020/6/2 15:34
 */
@RestController
public class ChargeController {
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private AreaDao areaDao;
    @RequestMapping("everyone/testJson")
    public void getJson(){
        GetImgUrl getImgUrl = new GetImgUrl();
        getImgUrl.getBaseUrl();
        String url = getImgUrl.getStorage() + "json.txt";
        File file = new File(url);
        try {
            //File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
            int ch = 0;
            String jsonStr = "";
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            JSONObject object = JSON.parseObject(jsonStr);
            System.out.println(object);
            for (int i = 11;i<83;i++){
                try {
                    Province province = new Province();
                    province.setProvinceId(i*10000);
                    province.setProvinceName(object.getJSONObject(String.valueOf(i*10000)).getString("name"));
//                provinceDao.insertSelective(province);
                    JSONObject objectCities = object.getJSONObject(String.valueOf(i*10000)).getJSONObject("child");
                    for (int c = 1;c<0;c++){
                        int cc = i*10000 + c*100;
                        try {
                          JSONObject  jsonObjectCity = objectCities.getJSONObject(String.valueOf(cc));
                            if (null == jsonObjectCity.getString("name")){
                                c = -1;
                                break;
                            }
                        } catch (Exception e) {
                            c = -1;
                            e.printStackTrace();
                            break;
                        }
                        JSONObject  jsonObjectCity = objectCities.getJSONObject(String.valueOf(cc));
                        City city = new City();
                        city.setCityId(cc);
                        city.setCityName(jsonObjectCity.getString("name"));

    //                    cityDao.insertSelective(city);
                        JSONObject jsonObjectAreas = jsonObjectCity.getJSONObject("child");

                        for (int a = 1;a<0;a++){
                            int aa = i*10000 + c*100 + a;
                            try {
                                String areaName = jsonObjectAreas.getString(String.valueOf(aa));

                                if ( null == areaName){
                                    a = -1;
                                    break;
                                }
                            } catch (Exception e) {
                                a = -1;
                                e.printStackTrace();
                                break;
                            }
                            Area area = new Area();
                            String areaName = jsonObjectAreas.getString(String.valueOf(aa));
                            area.setAreaId(aa);
                            area.setAreaName(areaName);
                            System.out.println(area);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        } catch (Exception e) {
e.printStackTrace();
        }
    }
}
