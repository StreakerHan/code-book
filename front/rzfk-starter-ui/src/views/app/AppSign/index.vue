<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="会员用户ID" prop="memberId">
        <el-input
          v-model="queryParams.memberId"
          placeholder="请输入会员用户ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="会员用户名" prop="memberName">
        <el-input
          v-model="queryParams.memberName"
          placeholder="请输入会员用户名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="连续签到天数" prop="signDay">
        <el-input
          v-model="queryParams.signDay"
          placeholder="请输入连续签到天数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="签到日 " prop="day">
        <el-input
          v-model="queryParams.day"
          placeholder="请输入签到日 "
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
          v-hasPermi="['app:AppSign:add']"
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
          v-hasPermi="['app:AppSign:edit']"
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
          v-hasPermi="['app:AppSign:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['app:AppSign:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="AppSignList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" v-if="true"/>
      <el-table-column label="会员用户ID" align="center" prop="memberId" />
      <el-table-column label="会员用户名" align="center" prop="memberName" />
      <el-table-column label="连续签到天数" align="center" prop="signDay" />
      <el-table-column label="签到日 " align="center" prop="day" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['app:AppSign:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['app:AppSign:remove']"
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

    <!-- 添加或修改用户签到记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="会员用户ID" prop="memberId">
          <el-input v-model="form.memberId" placeholder="请输入会员用户ID" />
        </el-form-item>
        <el-form-item label="会员用户名" prop="memberName">
          <el-input v-model="form.memberName" placeholder="请输入会员用户名" />
        </el-form-item>
        <el-form-item label="连续签到天数" prop="signDay">
          <el-input v-model="form.signDay" placeholder="请输入连续签到天数" />
        </el-form-item>
        <el-form-item label="签到日 " prop="day">
          <el-input v-model="form.day" placeholder="请输入签到日 " />
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
import { listAppSign, getAppSign, delAppSign, addAppSign, updateAppSign, exportAppSign } from "@/api/app/AppSign";

export default {
  name: "AppSign",
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
      // 用户签到记录表格数据
      AppSignList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        memberId: undefined,        memberName: undefined,        signDay: undefined,        day: undefined      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "ID不能为空", trigger: "blur" }
        ],        memberId: [
          { required: true, message: "会员用户ID不能为空", trigger: "blur" }
        ],        memberName: [
          { required: true, message: "会员用户名不能为空", trigger: "blur" }
        ],        signDay: [
          { required: true, message: "连续签到天数不能为空", trigger: "blur" }
        ],        day: [
          { required: true, message: "签到日 不能为空", trigger: "blur" }
        ]      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询用户签到记录列表 */
    getList() {
      this.loading = true;
      listAppSign(this.queryParams).then(response => {
        this.AppSignList = response.rows;
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
        id: undefined,        createTime: undefined,        memberId: undefined,        memberName: undefined,        signDay: undefined,        day: undefined      };
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
      this.title = "添加用户签到记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getAppSign(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改用户签到记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateAppSign(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addAppSign(this.form).then(response => {
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
        return delAppSign(ids);
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
        this.$confirm('是否确认导出所有用户签到记录数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
        }).then(() => {
            this.exportLoading = true;
            return exportAppSign(queryParams);
        }).then(response => {
            this.download(response.msg);
            this.exportLoading = false;
        })
    }
  }
};
</script>
