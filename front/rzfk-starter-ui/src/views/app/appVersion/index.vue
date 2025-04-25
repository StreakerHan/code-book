<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="下载地址" prop="downloadUrl">
        <el-input
          v-model="queryParams.downloadUrl"
          placeholder="请输入下载地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否强制更新" prop="forceUpdate">
        <el-input
          v-model="queryParams.forceUpdate"
          placeholder="请输入是否强制更新"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="版本名称" prop="versionName">
        <el-input
          v-model="queryParams.versionName"
          placeholder="请输入版本名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="版本更新时间" prop="versionUpdateDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.versionUpdateDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择版本更新时间">
        </el-date-picker>
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
          v-hasPermi="['app:appVersion:add']"
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
          v-hasPermi="['app:appVersion:edit']"
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
          v-hasPermi="['app:appVersion:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['app:appVersion:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="appVersionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" v-if="true"/>
      <el-table-column label="更新内容" align="center" prop="content" />
      <el-table-column label="下载地址" align="center" prop="downloadUrl" />
      <el-table-column label="是否强制更新" align="center" prop="forceUpdate" />
      <el-table-column label="类型" align="center" prop="type" />
      <el-table-column label="版本" align="center" prop="version" />
      <el-table-column label="版本名称" align="center" prop="versionName" />
      <el-table-column label="版本更新时间" align="center" prop="versionUpdateDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.versionUpdateDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['app:appVersion:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['app:appVersion:remove']"
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

    <!-- 添加或修改app版本管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="更新内容">
          <el-input v-model="form.content" placeholder="请输入更新内容" />
        </el-form-item>
        <el-form-item label="下载地址" prop="downloadUrl">
          <el-input v-model="form.downloadUrl" placeholder="请输入下载地址" />
        </el-form-item>
        <el-form-item label="是否强制更新" prop="forceUpdate">
          <el-input v-model="form.forceUpdate" placeholder="请输入是否强制更新" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-input v-model="form.type" placeholder="请输入类型" />
        </el-form-item>
        <el-form-item label="版本" prop="version">
          <el-input v-model="form.version" placeholder="请输入版本(100)" />
        </el-form-item>
        <el-form-item label="版本名称" prop="versionName">
          <el-input v-model="form.versionName" placeholder="请输入版本名称(1.0.0)" />
        </el-form-item>
        <!-- <el-form-item label="版本更新时间" prop="versionUpdateDate">
          <el-date-picker clearable size="small"
            v-model="form.versionUpdateDate"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择版本更新时间">
          </el-date-picker>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAppVersion, getAppVersion, delAppVersion, addAppVersion, updateAppVersion, exportAppVersion } from "@/api/app/appVersion";

export default {
  name: "AppVersion",
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
      // app版本管理表格数据
      appVersionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        content: undefined,        downloadUrl: undefined,        forceUpdate: undefined,        type: undefined,        versionName: undefined,        versionUpdateDate: undefined      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {    content: [
          { required: true, message: "更新内容不能为空", trigger: "blur" }
        ],        downloadUrl: [
          { required: true, message: "下载地址不能为空", trigger: "blur" }
        ],        forceUpdate: [
          { required: true, message: "是否强制更新不能为空", trigger: "blur" }
        ],        type: [
          { required: true, message: "类型不能为空", trigger: "change" }
        ],        versionName: [
          { required: true, message: "版本名称不能为空", trigger: "blur" }
        ]  }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询app版本管理列表 */
    getList() {
      this.loading = true;
      listAppVersion(this.queryParams).then(response => {
        this.appVersionList = response.rows;
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
        id: undefined,        createBy: undefined,        createTime: undefined,        content: undefined,        downloadUrl: undefined,        forceUpdate: undefined,        type: undefined,        version: undefined,        versionName: undefined,        versionUpdateDate: undefined      };
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
      this.title = "添加app版本管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getAppVersion(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改app版本管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateAppVersion(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addAppVersion(this.form).then(response => {
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
        return delAppVersion(ids);
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
        this.$confirm('是否确认导出所有app版本管理数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
        }).then(() => {
            this.exportLoading = true;
            return exportAppVersion(queryParams);
        }).then(response => {
            this.download(response.msg);
            this.exportLoading = false;
        })
    }
  }
};
</script>
