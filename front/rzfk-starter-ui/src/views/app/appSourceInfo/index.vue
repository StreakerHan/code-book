<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="资源名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入资源名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型id" prop="typeId">
        <el-input
          v-model="queryParams.typeId"
          placeholder="请输入类型id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="排序" prop="seq">
        <el-input
          v-model="queryParams.seq"
          placeholder="请输入排序"
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
      <el-form-item label="链接" prop="url">
        <el-input
          v-model="queryParams.url"
          placeholder="请输入链接"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="百度云" prop="link">
        <el-input
          v-model="queryParams.link"
          placeholder="请输入百度云"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-input
          v-model="queryParams.status"
          placeholder="请输入状态"
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
          v-hasPermi="['app:appSourceInfo:add']"
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
          v-hasPermi="['app:appSourceInfo:edit']"
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
          v-hasPermi="['app:appSourceInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['app:appSourceInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="appSourceInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="百度云" align="center" prop="id" v-if="true"/>
      <el-table-column label="资源名称" align="center" prop="name" />
      <el-table-column label="类型id" align="center" prop="typeId" />
      <el-table-column label="排序" align="center" prop="seq" />
      <el-table-column label="类型" align="center" prop="type" />
      <el-table-column label="链接" align="center" prop="url" />
      <el-table-column label="百度云" align="center" prop="link" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['app:appSourceInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['app:appSourceInfo:remove']"
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

    <!-- 添加或修改资源信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="资源名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入资源名称" />
        </el-form-item>
        <el-form-item label="封面">
          <imageUpload v-model="form.img"/>
        </el-form-item>
        <el-form-item label="类型" prop="typeId">
           <el-select v-model="form.typeId" placeholder="请选择类型">
            <el-option v-for="dict in type"
              :key="dict.id"
              :value="dict.id"
              :label="dict.name"/>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="seq">
          <el-input v-model="form.seq" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select
            v-model="form.type"
            filterable
            allow-create
            default-first-option
            placeholder="请输入类型">
            <el-option label="video" value="video">
            </el-option>
            <el-option label="image" value="image">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="链接" prop="url">
          <el-input v-model="form.url" placeholder="请输入链接" />
        </el-form-item>
        <el-form-item label="百度云" prop="link">
          <el-input v-model="form.link" placeholder="请输入百度云" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select
            v-model="form.status"
            filterable
            allow-create
            default-first-option
            placeholder="请输入类型">
            <el-option label="1" value="1">
            </el-option>
            <el-option label="0" value="0">
            </el-option>
          </el-select>
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
import { listAppSourceInfo, getAppSourceInfo, delAppSourceInfo, addAppSourceInfo, updateAppSourceInfo, exportAppSourceInfo } from "@/api/app/appSourceInfo";
import { listAppSourceType } from "@/api/app/appSourceType"

export default {
  name: "AppSourceInfo",
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
      // 资源信息表格数据
      appSourceInfoList: [],
      type:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,        typeId: undefined,        seq: undefined,        type: undefined,        url: undefined,        link: undefined,        status: undefined      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {   name: [
          { required: true, message: "资源名称不能为空", trigger: "blur" }
        ],        typeId: [
          { required: true, message: "类型id不能为空", trigger: "blur" }
        ],        seq: [
          { required: true, message: "排序不能为空", trigger: "blur" }
        ]    }
    };
  },
  created() {
    this.getList();
    this.getTypeList();
  },
  methods: {
    /** 查询资源信息列表 */
    getTypeList() {
      this.loading = true;
      listAppSourceType({
        pageNum: 1,
        pageSize: 1000}).then(response => {
        this.type = response.data;
        this.loading = false;
      });
    },
    /** 查询资源信息列表 */
    getList() {
      this.loading = true;
      listAppSourceInfo(this.queryParams).then(response => {
        this.appSourceInfoList = response.rows;
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
        id: undefined,        name: undefined,        typeId: undefined,        seq: undefined,        type: undefined,        url: undefined,        link: undefined,        status: undefined      };
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
      this.title = "添加资源信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getAppSourceInfo(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改资源信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateAppSourceInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addAppSourceInfo(this.form).then(response => {
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
        return delAppSourceInfo(ids);
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
        this.$confirm('是否确认导出所有资源信息数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
        }).then(() => {
            this.exportLoading = true;
            return exportAppSourceInfo(queryParams);
        }).then(response => {
            this.download(response.msg);
            this.exportLoading = false;
        })
    }
  }
};
</script>
