package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.DanhMucSanPhamDTO;
import com.ocha.boc.dto.MoHinhKinhDoanhDTO;
import com.ocha.boc.dto.SystemConfigDTO;
import com.ocha.boc.entity.DanhMucSanPham;
import com.ocha.boc.entity.GiayIn;
import com.ocha.boc.entity.MoHinhKinhDoanh;
import com.ocha.boc.entity.SystemConfig;
import com.ocha.boc.repository.DanhMucSanPhamRepository;
import com.ocha.boc.repository.MoHinhKinhDoanhRepository;
import com.ocha.boc.repository.SystemConfigRepository;
import com.ocha.boc.request.GiayInRequest;
import com.ocha.boc.response.SystemConfigurationResponse;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Slf4j
public class SystemConfigService {

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    @Autowired
    private DanhMucSanPhamRepository danhMucSanPhamRepository;

    @Autowired
    private MoHinhKinhDoanhRepository moHinhKinhDoanhRepository;

    public SystemConfigurationResponse getAllInformationAboutGiayIn() {
        SystemConfigurationResponse response = new SystemConfigurationResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_ALL_GIAY_IN_SYSTEM_CONFIGURATION_FAIL);
        try {
            List<GiayIn> listGiayIn = systemConfigRepository.findAllByListGiayInConfiguration();
            if (CollectionUtils.isNotEmpty(listGiayIn)) {
                SystemConfig systemConfig = new SystemConfig();
                systemConfig.setListGiayInConfiguration(listGiayIn);
                SystemConfigDTO systemConfigDTO = new SystemConfigDTO(systemConfig);
                response.setObject(systemConfigDTO);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            }
        } catch (Exception e) {
            log.error("Error when getAllSystemConfigurationInformation: ", e);
        }
        return response;
    }


    public SystemConfigurationResponse createNewGiayInInformation(GiayInRequest request) {
        SystemConfigurationResponse response = new SystemConfigurationResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.CREATE_NEW_GIAY_IN_INFORMATION_FAIL);
        try {
            if (request != null) {
                GiayIn giayIn = new GiayIn();
                giayIn.setDescription(request.getDescription());
                giayIn.setPrice(request.getPrice());
                giayIn.setRegion(request.getRegion());
                giayIn.setTitle(request.getTitle());
                List<GiayIn> listGiayIn = systemConfigRepository.findAllByListGiayInConfiguration();
                if (listGiayIn == null) {
                    listGiayIn = new ArrayList<GiayIn>();
                }
                listGiayIn.add(giayIn);
                SystemConfig systemConfig = new SystemConfig();
                systemConfig.setListGiayInConfiguration(listGiayIn);
                SystemConfigDTO systemConfigDTO = new SystemConfigDTO(systemConfig);
                response.setObject(systemConfigDTO);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
            }
        } catch (Exception e) {
            log.error("Error when createNewGiayInInformation: ", e);
        }
        return response;
    }

    public SystemConfigurationResponse deleteGiayInByTitle(String titleName) {
        SystemConfigurationResponse response = new SystemConfigurationResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.DELETE_GIAY_IN_BY_TITLE_FAIL);
        try {
            if (StringUtils.isNotEmpty(titleName)) {
                List<GiayIn> listGiayIn = systemConfigRepository.findAllByListGiayInConfiguration();
                if (CollectionUtils.isNotEmpty(listGiayIn)) {
                    int index = findIndexGiayInByTitle(titleName, listGiayIn);
                    listGiayIn.remove(index);
                    SystemConfig systemConfig = new SystemConfig();
                    systemConfig.setListGiayInConfiguration(listGiayIn);
                    SystemConfigDTO systemConfigDTO = new SystemConfigDTO(systemConfig);
                    response.setObject(systemConfigDTO);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                }
            }
        } catch (Exception e) {
            log.error("Error when deleteGiayInByTitle: ", e);
        }
        return response;
    }

    private int findIndexGiayInByTitle(String title, List<GiayIn> listGiayIn) {
        int index = 0;
        if (listGiayIn.stream().anyMatch(tmp -> tmp.getTitle().equalsIgnoreCase(title))) {
            index = IntStream.range(0, listGiayIn.size()).filter(i -> title.equalsIgnoreCase(listGiayIn.get(i).getTitle())).findFirst().getAsInt();
        }
        return index;
    }

    public AbstractResponse getAllInformationAboutDanhMucSanPham() {
        AbstractResponse response = new AbstractResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_ALL_DANH_MUC_SAN_PHAM_FAIL);
        try {
            List<DanhMucSanPham> results = danhMucSanPhamRepository.findAll();
            if (CollectionUtils.isEmpty(results)) {
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.GET_ALL_DANH_MUC_SAN_PHAM_DOESNT_EXISTED);
                return response;
            }
            List<DanhMucSanPhamDTO> danhMucSanPhamDTOList = new ArrayList<DanhMucSanPhamDTO>();
            for (DanhMucSanPham danhMucSanPham : results) {
                DanhMucSanPhamDTO temp = new DanhMucSanPhamDTO(danhMucSanPham);
                danhMucSanPhamDTOList.add(temp);
            }
            response.setObjects(danhMucSanPhamDTOList);
            response.setTotalResultCount((long) danhMucSanPhamDTOList.size());
            response.setSuccess(Boolean.TRUE);
        } catch (Exception e) {
            log.error("Exception while get all information about danh muc san pham: ", e);
        }
        return response;
    }


    public AbstractResponse getAllInformationAboutMoHinhKinhDoanh() {
        AbstractResponse response = new AbstractResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_ALL_MO_HINH_KINH_DOANH_FAIL);
        try {
            List<MoHinhKinhDoanh> results = moHinhKinhDoanhRepository.findAll();
            if(CollectionUtils.isEmpty(results)){
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.GET_ALL_MO_HINH_KINH_DOANH_DOESNT_EXISTED);
            }
            List<MoHinhKinhDoanhDTO> moHinhKinhDoanhDTOList = new ArrayList<MoHinhKinhDoanhDTO>();
            for(MoHinhKinhDoanh moHinhKinhDoanh: results){
                MoHinhKinhDoanhDTO temp = new MoHinhKinhDoanhDTO(moHinhKinhDoanh);
                moHinhKinhDoanhDTOList.add(temp);
            }
            response.setObjects(moHinhKinhDoanhDTOList);
            response.setTotalResultCount((long)moHinhKinhDoanhDTOList.size());
            response.setSuccess(Boolean.TRUE);
        } catch (Exception e) {
            log.error("Exception while get all information about mo hinh kinh doanh: ", e);
        }
        return response;
    }
}
