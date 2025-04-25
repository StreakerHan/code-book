<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="108px">
      <el-form-item label="删除标志" prop="deleteFlag">
        <el-input
          v-model="queryParams.deleteFlag"
          placeholder="请输入删除标志"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="区域编码" prop="adCode">
        <el-input
          v-model="queryParams.adCode"
          placeholder="请输入区域编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="城市代码" prop="cityCode">
        <el-input
          v-model="queryParams.cityCode"
          placeholder="请输入城市代码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="行政区划级别" prop="level">
        <el-input
          v-model="queryParams.level"
          placeholder="请输入行政区划级别"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="父ID" prop="parentId">
        <el-input
          v-model="queryParams.parentId"
          placeholder="请输入父ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['app:appRegion:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['app:appRegion:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['app:appRegion:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['app:appRegion:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="appRegionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" v-if="true"/>
      <el-table-column label="删除标志 true/false 删除/未删除" align="center" prop="deleteFlag" />
      <el-table-column label="区域编码" align="center" prop="adCode" />
      <el-table-column label="区域中心点经纬度" align="center" prop="center" />
      <el-table-column label="城市代码" align="center" prop="cityCode" />
      <el-table-column label="行政区划级别" align="center" prop="level" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="排序" align="center" prop="orderNum" />
      <el-table-column label="父ID" align="center" prop="parentId" />
      <el-table-column label="行政地区路径" align="center" prop="path" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['app:appRegion:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['app:appRegion:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改全国区划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="删除标志 true/false 删除/未删除" prop="deleteFlag">
          <el-input v-model="form.deleteFlag" placeholder="请输入删除标志 true/false 删除/未删除" />
        </el-form-item>
        <el-form-item label="区域编码" prop="adCode">
          <el-input v-model="form.adCode" placeholder="请输入区域编码" />
        </el-form-item>
        <el-form-item label="区域中心点经纬度" prop="center">
          <el-input v-model="form.center" placeholder="请输入区域中心点经纬度" />
        </el-form-item>
        <el-form-item label="城市代码" prop="cityCode">
          <el-input v-model="form.cityCode" placeholder="请输入城市代码" />
        </el-form-item>
        <el-form-item label="行政区划级别" prop="level">
          <el-input v-model="form.level" placeholder="请输入行政区划级别" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input v-model="form.orderNum" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="父ID" prop="parentId">
          <el-input v-model="form.parentId" placeholder="请输入父ID" />
        </el-form-item>
        <el-form-item label="行政地区路径" prop="path">
          <el-input v-model="form.path" placeholder="请输入行政地区路径" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAppRegion, getAppRegion, delAppRegion, addAppRegion, updateAppRegion, exportAppRegion } from "@/api/app/appRegion";

export default {
  name: "AppRegion",
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 全国区划表格数据
      appRegionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deleteFlag: undefined,        adCode: undefined,        center: undefined,        cityCode: undefined,        level: undefined,        name: undefined,        orderNum: undefined,        parentId: undefined,        path: undefined      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "ID不能为空", trigger: "blur" }
        ],        deleteFlag: [
          { required: true, message: "删除标志 true/false 删除/未删除不能为空", trigger: "blur" }
        ],        adCode: [
          { required: true, message: "区域编码不能为空", trigger: "blur" }
        ],        center: [
          { required: true, message: "区域中心点经纬度不能为空", trigger: "blur" }
        ],        cityCode: [
          { required: true, message: "城市代码不能为空", trigger: "blur" }
        ],        level: [
          { required: true, message: "行政区划级别不能为空", trigger: "blur" }
        ],        name: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],        orderNum: [
          { required: true, message: "排序不能为空", trigger: "blur" }
        ],        parentId: [
          { required: true, message: "父ID不能为空", trigger: "blur" }
        ],        path: [
          { required: true, message: "行政地区路径不能为空", trigger: "blur" }
        ]      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询全国区划列表 */
    getList() {
      this.loading = true;
      listAppRegion(this.queryParams).then(response => {
        this.appRegionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,        createBy: undefined,        createTime: undefined,        deleteFlag: undefined,        updateBy: undefined,        updateTime: undefined,        adCode: undefined,        center: undefined,        cityCode: undefined,        level: undefined,        name: undefined,        orderNum: undefined,        parentId: undefined,        path: undefined      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加全国区划";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getAppRegion(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改全国区划";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateAppRegion(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addAppRegion(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除该数据项？').then(() => {
        this.loading = true;
        return delAppRegion(ids);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有全国区划数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
        }).then(() => {
            this.exportLoading = true;
            return exportAppRegion(queryParams);
        }).then(response => {
            this.download(response.msg);
            this.exportLoading = false;
        })
    }
  }
};
</script>
