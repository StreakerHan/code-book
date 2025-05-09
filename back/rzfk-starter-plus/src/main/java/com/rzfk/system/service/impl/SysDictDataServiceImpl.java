package com.rzfk.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rzfk.common.core.domain.entity.SysDictData;
import com.rzfk.common.core.page.TableDataInfo;
import com.rzfk.common.utils.DictUtils;
import com.rzfk.common.utils.PageUtils;
import com.rzfk.system.mapper.SysDictDataMapper;
import com.rzfk.system.service.ISysDictDataService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {

	@Override
	public TableDataInfo<SysDictData> selectPageDictDataList(SysDictData dictData) {
		LambdaQueryWrapper<SysDictData> lqw = new LambdaQueryWrapper<SysDictData>()
			.eq(StrUtil.isNotBlank(dictData.getDictType()), SysDictData::getDictType, dictData.getDictType())
			.like(StrUtil.isNotBlank(dictData.getDictLabel()), SysDictData::getDictLabel, dictData.getDictLabel())
			.eq(StrUtil.isNotBlank(dictData.getStatus()), SysDictData::getStatus, dictData.getStatus())
			.orderByAsc(SysDictData::getDictSort);
		return PageUtils.buildDataInfo(page(PageUtils.buildPage(), lqw));
	}

	/**
	 * 根据条件分页查询字典数据
	 *
	 * @param dictData 字典数据信息
	 * @return 字典数据集合信息
	 */
	@Override
	public List<SysDictData> selectDictDataList(SysDictData dictData) {
		return list(new LambdaQueryWrapper<SysDictData>()
			.eq(StrUtil.isNotBlank(dictData.getDictType()), SysDictData::getDictType, dictData.getDictType())
			.like(StrUtil.isNotBlank(dictData.getDictLabel()), SysDictData::getDictLabel, dictData.getDictLabel())
			.eq(StrUtil.isNotBlank(dictData.getStatus()), SysDictData::getStatus, dictData.getStatus())
			.orderByAsc(SysDictData::getDictSort));
	}

	/**
	 * 根据字典类型和字典键值查询字典数据信息
	 *
	 * @param dictType  字典类型
	 * @param dictValue 字典键值
	 * @return 字典标签
	 */
	@Override
	public String selectDictLabel(String dictType, String dictValue) {
		return getOne(new LambdaQueryWrapper<SysDictData>()
			.select(SysDictData::getDictLabel)
			.eq(SysDictData::getDictType, dictType)
			.eq(SysDictData::getDictValue, dictValue))
			.getDictLabel();
	}

	/**
	 * 根据字典数据ID查询信息
	 *
	 * @param dictCode 字典数据ID
	 * @return 字典数据
	 */
	@Override
	public SysDictData selectDictDataById(Long dictCode) {
		return getById(dictCode);
	}

	/**
	 * 批量删除字典数据信息
	 *
	 * @param dictCodes 需要删除的字典数据ID
	 * @return 结果
	 */
	@Override
	public void deleteDictDataByIds(Long[] dictCodes) {
		for (Long dictCode : dictCodes) {
			SysDictData data = selectDictDataById(dictCode);
			List<SysDictData> dictDatas = baseMapper.selectDictDataByType(data.getDictType());
			DictUtils.setDictCache(data.getDictType(), dictDatas);
		}
		baseMapper.deleteBatchIds(Arrays.asList(dictCodes));
	}

	/**
	 * 新增保存字典数据信息
	 *
	 * @param data 字典数据信息
	 * @return 结果
	 */
	@Override
	public int insertDictData(SysDictData data) {
		int row = baseMapper.insert(data);
		if (row > 0) {
			List<SysDictData> dictDatas = baseMapper.selectDictDataByType(data.getDictType());
			DictUtils.setDictCache(data.getDictType(), dictDatas);
		}
		return row;
	}

	/**
	 * 修改保存字典数据信息
	 *
	 * @param data 字典数据信息
	 * @return 结果
	 */
	@Override
	public int updateDictData(SysDictData data) {
		int row = baseMapper.updateById(data);
		if (row > 0) {
			List<SysDictData> dictDatas = baseMapper.selectDictDataByType(data.getDictType());
			DictUtils.setDictCache(data.getDictType(), dictDatas);
		}
		return row;
	}
}
