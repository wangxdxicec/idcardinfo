package com.zhenhappy.ems.manager.action.user;

import com.zhenhappy.ems.dto.BaseResponse;
import com.zhenhappy.ems.manager.action.BaseAction;
import com.zhenhappy.ems.manager.dao.IDCardInfoDao;
import com.zhenhappy.ems.manager.dao.TFanPaInfoDataDao;
import com.zhenhappy.ems.manager.dto.*;
import com.zhenhappy.ems.manager.entity.TFanPaInfo;
import com.zhenhappy.ems.manager.entity.TFanPaListInfo;
import com.zhenhappy.ems.manager.entity.TIDCardInfo;
import com.zhenhappy.ems.manager.service.IDCardInfoServiceImp;
import com.zhenhappy.ems.manager.service.TFanPaInfoServiceImp;
import com.zhenhappy.system.SystemConfig;
import com.zhenhappy.util.Page;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import sun.misc.BASE64Decoder;

/**
 * Created by wangxd on 2016/4/5.
 */
@Controller
@RequestMapping(value = "user")
@SessionAttributes(value = ManagerPrinciple.MANAGERPRINCIPLE)
public class QRCodeScanAction extends BaseAction {
    private static Logger log = Logger.getLogger(QRCodeScanAction.class);

    @Autowired
    private IDCardInfoServiceImp idCardInfoServiceImp;
    @Autowired
    private IDCardInfoDao idCardInfoDao;

    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private TFanPaInfoServiceImp fanPaInfoServiceImp;
    @Autowired
    private TFanPaInfoDataDao tFanPaInfoDataDao;

    @ResponseBody
    @RequestMapping(value = "saveIdCardInfo", method = RequestMethod.POST)
    public BaseResponse saveIdCardInfo(@RequestParam(value = "name") String name,
                                       @RequestParam(value = "sex") String sex,
                                       @RequestParam(value = "nation") String nation,
                                       @RequestParam(value = "born") String born,
                                       @RequestParam(value = "address") String address,
                                       @RequestParam(value = "cardno") String cardno,
                                       @RequestParam(value = "issuedat") String issuedat,
                                       @RequestParam(value = "effecteddate") String effecteddate,
                                       @RequestParam(value = "expireddate") String expireddate,
                                       @RequestParam(value = "samid") String samid,
                                       @RequestParam(value = "picture") String picture,
                                       @RequestParam(value = "pic") String pic,
                                       @RequestParam(value = "picturylen") String picturylen,
                                       @RequestParam(value = "barcode") String barcode,
                                       @RequestParam(value = "remark") String remark,
                                       /*@RequestParam("pic") MultipartFile pic,*/
                                       HttpServletRequest request) {
        BaseResponse response = new BaseResponse();
        TIDCardInfo tidCardInfo = idCardInfoServiceImp.loadIDCardinfoByCardNum(cardno);
        if(tidCardInfo != null){
            if(tidCardInfo.getScannum() != null && tidCardInfo.getScannum()>2){
                response.setResultCode(1);
                response.setDescription("身份证多次被刷过，请重新提供，谢谢！");
            }else {
                response.setResultCode(0);
                tidCardInfo.setScannum(tidCardInfo.getScannum() + 1);
                tidCardInfo.setBarcode(barcode + "||" + tidCardInfo.getBarcode());
                tidCardInfo.setRemark(remark);
                response.setDescription(String.valueOf(tidCardInfo.getScannum()));
                idCardInfoServiceImp.updateIDCardinfo(tidCardInfo);
            }
        }else {
            tidCardInfo = new TIDCardInfo();
            tidCardInfo.setName(name);
            tidCardInfo.setSex(sex);
            tidCardInfo.setNation(nation);
            tidCardInfo.setBorn(born);
            tidCardInfo.setAddress(address);
            tidCardInfo.setCardno(cardno);
            tidCardInfo.setIssuedaddress(issuedat);
            tidCardInfo.setEffectdate(effecteddate);
            tidCardInfo.setExpiredate(expireddate);
            tidCardInfo.setSamid(samid);
            //tidCardInfo.setPicimage(pic);
            tidCardInfo.setPicimageaddress("C:\\Program Files\\Apache Software Foundation\\appendix\\idcardimage\\images\\" + cardno + ".jpg");
            tidCardInfo.setPiccode(picture);
            tidCardInfo.setPiclength(picturylen);
            tidCardInfo.setBarcode(barcode);
            tidCardInfo.setScannum(1);
            tidCardInfo.setCreatetime(new Date());
            tidCardInfo.setRemark(remark);
            idCardInfoServiceImp.saveIDCardinfo(tidCardInfo);
            response.setResultCode(0);
            response.setDescription(String.valueOf(tidCardInfo.getScannum()));
        }
        return response;
    }

    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            String oldPathEx = oldPath.substring(8, oldPath.length());
            File oldfile = new File(oldPathEx);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldfile); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    //System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.flush();

                new File(oldPathEx).delete();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }
    }

    public File upload(@RequestParam MultipartFile oldPath, String newPath, String fileName){
        if(StringUtils.isEmpty(fileName))
            fileName = fileName + "." + FilenameUtils.getExtension(oldPath.getOriginalFilename());
        //FileUtils.copyInputStreamToFile(companyLogo.getInputStream(), new File(fileName));
        File targetFile = new File(newPath, fileName);
        if(!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            oldPath.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetFile;
    }

    public static String fileload(String url, File file) {
        String body = "{}";
        if (url == null || url.equals("")) {
            return "参数不合法";
        }
        if (!file.exists()) {
            return "要上传的文件名不存在";
        }

        PostMethod postMethod = new PostMethod(url);
        try {
            // FilePart：用来上传文件的类,file即要上传的文件
            FilePart fp = new FilePart("file", file);
            Part[] parts = { fp };

            // 对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
            MultipartRequestEntity mre = new MultipartRequestEntity(parts, postMethod.getParams());
            postMethod.setRequestEntity(mre);

            HttpClient client = new HttpClient();
            // 由于要上传的文件可能比较大 , 因此在此设置最大的连接超时时间
            client.getHttpConnectionManager().getParams() .setConnectionTimeout(50000);

            int status = client.executeMethod(postMethod);
            if (status == HttpStatus.SC_OK) {
                InputStream inputStream = postMethod.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = br.readLine()) != null) {
                    stringBuffer.append(str);
                }

                body = stringBuffer.toString();

            } else {
                body = "fail";
            }
        } catch (Exception e) {
            log.warn("上传文件异常", e);
        } finally {
            // 释放连接
            postMethod.releaseConnection();
        }
        return body;
    }

    @ResponseBody
    @RequestMapping(value = "queryIdCardInfo", method = RequestMethod.POST)
    public BaseResponse queryIdCardInfo(@RequestParam(value = "cardno") String cardno,
                                        @RequestParam(value = "imagepath") String imagepath,
                                       HttpServletRequest request) {
        BaseResponse response = new BaseResponse();
        TIDCardInfo tidCardInfo = idCardInfoServiceImp.loadIDCardinfoByCardNum(cardno);
        if(StringUtils.isNotEmpty(imagepath) && imagepath.length()>8){
            //String imagePath = imagpath.substring(8, imagpath.length());
            if (new File(imagepath).exists()) {
                //new File(imagepath).delete();
            }
        }
        if(tidCardInfo != null){
            if(tidCardInfo.getScannum() != null && tidCardInfo.getScannum()>2){
                response.setResultCode(1);
                response.setDescription("身份证多次被刷过，请重新提供，谢谢！");
            }else {
                response.setResultCode(0);
                response.setDescription(String.valueOf(tidCardInfo.getScannum()));
            }
        }else {
            response.setResultCode(0);
            response.setDescription("");
        }

        /*String fileName = systemConfig.getVal("appendix_directory") + "/" + cardno + ".jpg";
        //String oldPathEx = imagepath.substring(8, imagepath.length());
        File oldfile = new File(imagepath);
        if (oldfile.exists()) { //文件存在时
            try {
                InputStream inStream = new FileInputStream(oldfile); //读入原文件
                FileUtils.copyInputStreamToFile(inStream, new File(fileName));
                idCardInfoServiceImp.saveIDCardinfo(tidCardInfo);
                response.setResultCode(0);
                response.setDescription(String.valueOf(tidCardInfo.getScannum()) + "--" + imagepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        return response;
    }

    /**
     * redirect to id card info list page.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "idcardinfo", method = RequestMethod.GET)
    public ModelAndView redirectIdcardinfo(HttpServletRequest request, Locale locale) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/user/main/idcardinfo_new");
        return modelAndView;
    }

    /**
     * redirect to id card info list page.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "admin/idcard/info/list", method = RequestMethod.GET)
    public ModelAndView redirectAdminProjectList(HttpServletRequest request, Locale locale) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/user/main/admin_idcard_info_list");
        return modelAndView;
    }

    /**
     * 分页查询用户列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryIdCardInfoListByPage", method = RequestMethod.POST)
    public QueryUserListResponse queryIdCardListByPage(@ModelAttribute QueryUserListRequest request) {
        QueryUserListResponse response = null;
        try {
            response = queryIdCardInfoListByPage(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query user list error.", e);
        }
        return response;
    }

    /**
     * 分页获取用户列表
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @Transactional
    public QueryUserListResponse queryIdCardInfoListByPage(QueryUserListRequest request) throws UnsupportedEncodingException {
        List<String> conditions = new ArrayList<String>();

        //姓名
        if (StringUtils.isNotEmpty(request.getUserName())) {
            conditions.add(" (name like '%" + request.getUserName() + "%' OR name like '%" + new String(request.getUserName().getBytes("ISO-8859-1"),"GBK") + "%' OR name like '%" + new String(request.getUserName().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }
        //性别
        if (request.getUserSex() != null && -1 != request.getUserSex()) {
            conditions.add(" sex = '" + (request.getUserSex() == 0?"男":"女") + "' ");
        }
        //民族
        if (StringUtils.isNotEmpty(request.getUserNation())) {
            conditions.add(" (nation like '%" + request.getUserNation() + "%' OR nation like '%" + new String(request.getUserNation().getBytes("ISO-8859-1"),"GBK") + "%' OR nation like '%" + new String(request.getUserNation().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }
        //出生日期
        if (StringUtils.isNotEmpty(request.getUserBorn())) {
            conditions.add(" (born like '%" + request.getUserBorn() + "%' OR born like '%" + new String(request.getUserBorn().getBytes("ISO-8859-1"),"GBK") + "%' OR born like '%" + new String(request.getUserBorn().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }//地址
        if (StringUtils.isNotEmpty(request.getUserAddress())) {
            conditions.add(" (address like '%" + request.getUserAddress() + "%' OR address like '%" + new String(request.getUserAddress().getBytes("ISO-8859-1"),"GBK") + "%' OR address like '%" + new String(request.getUserAddress().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }
        //身份证号
        if (StringUtils.isNotEmpty(request.getUserCardNo())) {
            conditions.add(" (cardno like '%" + request.getUserCardNo() + "%' OR cardno like '%" + new String(request.getUserCardNo().getBytes("ISO-8859-1"),"GBK") + "%' OR cardno like '%" + new String(request.getUserCardNo().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }
        //签发机关
        if (StringUtils.isNotEmpty(request.getUserIssueAddress())) {
            conditions.add(" (issuedaddress like '%" + request.getUserIssueAddress() + "%' OR issuedaddress like '%" + new String(request.getUserIssueAddress().getBytes("ISO-8859-1"),"GBK") + "%' OR issuedaddress like '%" + new String(request.getUserIssueAddress().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }
        //起始有效日期
        if (StringUtils.isNotEmpty(request.getUserEffecToExpiretDate())) {
            conditions.add(" (effectdate like '%" + request.getUserEffecToExpiretDate() + "%' OR effectdate like '%" + new String(request.getUserEffecToExpiretDate().getBytes("ISO-8859-1"),"GBK") + "%' OR effectdate like '%" + new String(request.getUserEffecToExpiretDate().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }
        /*//截止有效日期
        if (StringUtils.isNotEmpty(request.getUserEffecToExpiretDate())) {
            conditions.add(" (expiredate like '%" + request.getUserEffecToExpiretDate() + "%' OR expiredate like '%" + new String(request.getUserEffecToExpiretDate().getBytes("ISO-8859-1"),"GBK") + "%' OR expiredate like '%" + new String(request.getUserEffecToExpiretDate().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }*/
        //条形码
        if(StringUtils.isNotEmpty(request.getUserBarCode())){
            conditions.add(" (barcode like '%" + request.getUserBarCode() + "%' OR barcode like '%" + new String(request.getUserBarCode().getBytes("ISO-8859-1"),"GBK") + "%' OR barcode like '%" + new String(request.getUserBarCode().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }

        String conditionsSql = StringUtils.join(conditions, " and ");
        String conditionsSqlNoOrder = "";
        String conditionsSqlOrder = "";
        if(StringUtils.isNotEmpty(conditionsSql)){
            conditionsSqlNoOrder = " where " + conditionsSql;
            conditionsSqlOrder = " where " + conditionsSql + " order by createtime desc ";
        }

        Page page = new Page();
        page.setPageSize(request.getRows());
        if(request.getPageIndex() != null){
            page.setPageIndex(request.getPageIndex());
        }else{
            page.setPageIndex(request.getPage());
        }

        List<QueryIdCardInfo> idCardInfoList = new ArrayList<QueryIdCardInfo>();
        idCardInfoList = idCardInfoDao.queryPageByHQL("select count(*) from TIDCardInfo " + conditionsSqlNoOrder,
                "select new com.zhenhappy.ems.manager.dto.QueryIdCardInfo(id, name, sex, nation, born, " +
                        "address, cardno, issuedaddress, effectdate, expiredate, barcode,scannum, picimageaddress) from TIDCardInfo " + conditionsSqlOrder, new Object[]{}, page);

        QueryUserListResponse response = new QueryUserListResponse();
        response.setResultCode(0);
        response.setRows(idCardInfoList);
        response.setTotal(page.getTotalCount());
        return response;
    }

    /**
     * 显示身份证图片
     * @param response
     * @param id
     */
    @RequestMapping(value = "showIdCardImagePage", method = RequestMethod.GET)
    public void showIdCardImagePage(HttpServletResponse response, @RequestParam("id") Integer id) {
        try{
            TIDCardInfo tidCardInfo = idCardInfoDao.query(id);
            if(tidCardInfo != null){
                BASE64Decoder decoder = new BASE64Decoder();
                //Base64解码
                byte[] b = decoder.decodeBuffer(tidCardInfo.getPiccode());
                for (int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {//调整异常数据
                        b[i] += 256;
                    }
                }
                //生成jpeg图片
                OutputStream out = new FileOutputStream(tidCardInfo.getPicimageaddress());
                out.write(b);
                out.flush();
                out.close();

                OutputStream outputStream = response.getOutputStream();
                if(tidCardInfo.getPicimageaddress().toLowerCase().contains(".pdf")){
                    response.setContentType("application/pdf");
                }
                File logo = new File(tidCardInfo.getPicimageaddress());
                if (!logo.exists())
                    return;
                FileUtils.copyFile(logo, outputStream);
                outputStream.close();
                outputStream.flush();
            }
        }catch (Exception e){

        }
    }

    /**
     * 显示身份证图片
     * @param response
     * @param cardno
     */
    @RequestMapping(value = "showIdCardImagePageByCardNo", method = RequestMethod.GET)
    public void showIdCardImagePageByCardNo(HttpServletResponse response, @RequestParam("cardno") String cardno) {
        try{
            if(StringUtils.isNotEmpty(cardno)){
                TIDCardInfo tidCardInfo = idCardInfoServiceImp.loadIDCardinfoByCardNum(cardno);
                if(tidCardInfo != null){
                    BASE64Decoder decoder = new BASE64Decoder();
                    //Base64解码
                    byte[] b = decoder.decodeBuffer(tidCardInfo.getPiccode());
                    for (int i = 0; i < b.length; ++i) {
                        if (b[i] < 0) {//调整异常数据
                            b[i] += 256;
                        }
                    }
                    //生成jpeg图片
                    OutputStream out = new FileOutputStream(tidCardInfo.getPicimageaddress());
                    out.write(b);
                    out.flush();
                    out.close();

                    OutputStream outputStream = response.getOutputStream();
                    if(tidCardInfo.getPicimageaddress().toLowerCase().contains(".pdf")){
                        response.setContentType("application/pdf");
                    }
                    File logo = new File(tidCardInfo.getPicimageaddress());
                    if (!logo.exists())
                        return;
                    FileUtils.copyFile(logo, outputStream);
                    outputStream.close();
                    outputStream.flush();
                }
            }
        }catch (Exception e){

        }
    }
    /*public void showIdCardImagePage(HttpServletResponse response, @RequestParam("id") Integer id) {
        try {
            TIDCardInfo tidCardInfo = idCardInfoDao.query(id);
            if(tidCardInfo != null){
                String logoFileName = tidCardInfo.getPicimageaddress();
                if (StringUtils.isNotEmpty(logoFileName)) {
                    OutputStream outputStream = response.getOutputStream();
                    if(logoFileName.toLowerCase().contains(".pdf")){
                        response.setContentType("application/pdf");
                    }
                    File logo = new File(logoFileName);
                    if (!logo.exists())
                        return;
                    FileUtils.copyFile(logo, outputStream);
                    outputStream.close();
                    outputStream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * redirect to id card info list page.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "fanpalist", method = RequestMethod.GET)
    public ModelAndView redirectFanPaList(HttpServletRequest request, Locale locale) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/user/main/fan_pa_info_list");
        return modelAndView;
    }

    /**
     * 分页查询反扒用户列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryFanPaInfoListByPage", method = RequestMethod.POST)
    public QueryUserListResponse queryFanPaInfoListByPage(@ModelAttribute QueryFanPaListRequest request) {
        QueryUserListResponse response = null;
        try {
            response = queryFanPaDataListByPage(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query user list error.", e);
        }
        return response;
    }

    /**
     * 分页获取反扒用户列表
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @Transactional
    public QueryUserListResponse queryFanPaDataListByPage(QueryFanPaListRequest request) throws UnsupportedEncodingException {
        List<String> conditions = new ArrayList<String>();
        //身份证号
        if (StringUtils.isNotEmpty(request.getCardno())) {
            conditions.add(" (e.cardno like '%" + request.getCardno() + "%' OR e.cardno like '%" + new String(request.getCardno().getBytes("ISO-8859-1"),"GBK") + "%' OR e.cardno like '%" + new String(request.getCardno().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }
        //条形码
        if(StringUtils.isNotEmpty(request.getBarcode())){
            conditions.add(" (e.barcode like '%" + request.getBarcode() + "%' OR e.barcode like '%" + new String(request.getBarcode().getBytes("ISO-8859-1"),"GBK") + "%' OR e.barcode like '%" + new String(request.getBarcode().getBytes("ISO-8859-1"),"utf-8") + "%') ");
        }
        //时间
        if (request.getDate() != null) {
            conditions.add(" e.date like '%" + new String(request.getDate().getBytes("ISO-8859-1"),"utf-8") + "%'");
        }

        String conditionsSql = StringUtils.join(conditions, " and ");
        String conditionsSqlNoOrder = "";
        String conditionsSqlOrder = "";
        if(StringUtils.isNotEmpty(conditionsSql)){
            conditionsSqlNoOrder = " where " + conditionsSql;
            conditionsSqlOrder = " where " + conditionsSql + " order by e.creatime desc ";
        }

        Page page = new Page();
        page.setPageSize(request.getRows());
        if(request.getPageIndex() != null){
            page.setPageIndex(request.getPageIndex());
        }else{
            page.setPageIndex(request.getPage());
        }

        List<QueryFanPaInfo> tFanPaListInfoArrayList = new ArrayList<QueryFanPaInfo>();
        List<QueryFanPaInfoEx> tFanPaListInfoArrayList1 = new ArrayList<QueryFanPaInfoEx>();
        tFanPaListInfoArrayList = idCardInfoDao.queryPageByHQL("select count(*) from TFanPaListInfo e " + conditionsSqlNoOrder,
                "select new com.zhenhappy.ems.manager.dto.QueryFanPaInfo(id, cardno, barcode, creatime) from TFanPaListInfo e " + conditionsSqlOrder, new Object[]{}, page);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E");
        for(QueryFanPaInfo tFanPaListInfo:tFanPaListInfoArrayList){
            QueryFanPaInfoEx queryFanPaInfoEx = new QueryFanPaInfoEx();
            queryFanPaInfoEx.setCardno(tFanPaListInfo.getCardno());
            queryFanPaInfoEx.setBarcode(tFanPaListInfo.getBarcode());
            queryFanPaInfoEx.setCreatime(sdf.format(tFanPaListInfo.getCreatime()));
            tFanPaListInfoArrayList1.add(queryFanPaInfoEx);
        }
        QueryUserListResponse response = new QueryUserListResponse();
        response.setResultCode(0);
        response.setRows(tFanPaListInfoArrayList1);
        response.setTotal(page.getTotalCount());
        return response;
    }

    /**
     * 分页查询反扒用户列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "filterfanpalist", method = RequestMethod.POST)
    public BaseResponse filterfanpalist(@ModelAttribute QueryFanPaListRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            System.out.println("============================================开始过滤反扒名单============================================");
            List<TIDCardInfo> tidCardInfoList = idCardInfoServiceImp.loadIDCardinfoByUnFilter();
            if(tidCardInfoList != null && tidCardInfoList.size()>0){
                System.out.println("未过滤名单大小：" + tidCardInfoList.size());
            }
            if(tidCardInfoList != null){
                for(TIDCardInfo tidCardInfo:tidCardInfoList){
                    if(isExistFanPaList(tidCardInfo)){
                        TFanPaListInfo tFanPaListInfo = new TFanPaListInfo();
                        tFanPaListInfo.setCardno(tidCardInfo.getCardno());
                        if(StringUtils.isNotEmpty(tidCardInfo.getBarcode())){
                            tFanPaListInfo.setBarcode(tidCardInfo.getBarcode());
                        }
                        tFanPaListInfo.setCreatime(tidCardInfo.getCreatetime());
                        tFanPaInfoDataDao.create(tFanPaListInfo);
                    }
                    tidCardInfo.setFilterflag(1);
                    idCardInfoServiceImp.updateIDCardinfo(tidCardInfo);
                }
            }
            response.setResultCode(0);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query user list error.", e);
        }
        return response;
    }

    private boolean isExistFanPaList(TIDCardInfo tidCardInfo){
        boolean flag = false;
        if(tidCardInfo != null && StringUtils.isNotEmpty(tidCardInfo.getCardno())){
            TFanPaInfo tFanPaInfo = fanPaInfoServiceImp.loadTFanPaInfoByCardNum(tidCardInfo.getCardno());
            if(tFanPaInfo != null){
                flag = true;
            }else{
                flag = false;
            }
        }else{
            flag = false;
        }
        return flag;
    }
}
