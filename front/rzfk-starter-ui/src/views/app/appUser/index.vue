<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="108px">
      <el-form-item label="用户名称" prop="nickname">
        <el-input
          v-model="queryParams.nickname"
          placeholder="请输入用户名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="mobile">
        <el-input
          v-model="queryParams.mobile"
          placeholder="请输入手机号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="queryParams.sex" placeholder="请选择性别" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="邀请码" prop="inviteCode">
        <el-input
          v-model="queryParams.inviteCode"
          placeholder="请输入邀请码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="今日是否签到" prop="isSign">
        <el-input
          v-model="queryParams.isSign"
          placeholder="请输入今日是否签到"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择用户状态" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
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
          v-hasPermi="['app:appUser:add']"
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
          v-hasPermi="['app:appUser:edit']"
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
          v-hasPermi="['app:appUser:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['app:appUser:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="appUserList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" v-if="true"/>
      <el-table-column label="用户名称" align="center" prop="nickname" />
      <!-- <el-table-column label="密码" align="center" prop="password" /> -->
      <el-table-column label="真实姓名" align="center" prop="name" />
      <!-- <el-table-column label="第三方登录用户id" align="center" prop="socialUid" />
      <el-table-column label="第三方登录令牌" align="center" prop="socialToken" /> -->
      <el-table-column label="手机号码" align="center" prop="mobile" />
      <el-table-column label="邮箱地址" align="center" prop="email" />
      <!-- <el-table-column label="客户端唯一标识" align="center" prop="clientId" /> -->
      <!-- <el-table-column label="推送的令牌" align="center" prop="pushToken" /> -->
      <el-table-column label="性别" align="center" prop="sex" />
      <!-- <el-table-column label="用户注册来源" align="center" prop="source" /> -->
      <!-- <el-table-column label="第三方登录来源" align="center" prop="socialSource" /> -->
      <!-- <el-table-column label="头像" align="center" prop="avatar" /> -->
      <!-- <el-table-column label="省份" align="center" prop="province" />
      <el-table-column label="城市" align="center" prop="city" />
      <el-table-column label="区县" align="center" prop="area" />
      <el-table-column label="详细地址" align="center" prop="address" /> -->
      <el-table-column label="最后登录时间" align="center" prop="lastLoginTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastLoginTime) }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="生日" align="center" prop="birth" /> -->
      <el-table-column label="邀请码" align="center" prop="inviteCode" />
      <el-table-column label="今日是否签到" align="center" prop="isSign" />
      <el-table-column label="用户状态" align="center" prop="status" />
      <!-- <el-table-column label="经验值" align="center" prop="exp" />
      <el-table-column label="身份证号码" align="center" prop="idcard" /> -->
      <!-- <el-table-column label="我的签名" align="center" prop="sign" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['app:appUser:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['app:appUser:remove']"
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

    <!-- 添加或修改app用户对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名称" prop="nickname">
          <el-input v-model="form.nickname" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="真实姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入真实姓名" />
        </el-form-item>
        <el-form-item label="第三方登录用户id" prop="socialUid">
          <el-input v-model="form.socialUid" placeholder="请输入第三方登录用户id" />
        </el-form-item>
        <el-form-item label="第三方登录令牌" prop="socialToken">
          <el-input v-model="form.socialToken" placeholder="请输入第三方登录令牌" />
        </el-form-item>
        <el-form-item label="手机号码" prop="mobile">
          <el-input v-model="form.mobile" placeholder="请输入手机号码" />
        </el-form-item>
        <el-form-item label="邮箱地址" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱地址" />
        </el-form-item>
        <el-form-item label="客户端唯一标识" prop="clientId">
          <el-input v-model="form.clientId" placeholder="请输入客户端唯一标识" />
        </el-form-item>
        <el-form-item label="推送的令牌" prop="pushToken">
          <el-input v-model="form.pushToken" placeholder="请输入推送的令牌" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择性别">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="用户注册来源" prop="source">
          <el-input v-model="form.source" placeholder="请输入用户注册来源" />
        </el-form-item>
        <el-form-item label="第三方登录来源" prop="socialSource">
          <el-input v-model="form.socialSource" placeholder="请输入第三方登录来源" />
        </el-form-item>
        <el-form-item label="头像" prop="avatar">
          <el-input v-model="form.avatar" placeholder="请输入头像" />
        </el-form-item>
        <el-form-item label="省份" prop="province">
          <el-input v-model="form.province" placeholder="请输入省份" />
        </el-form-item>
        <el-form-item label="城市" prop="city">
          <el-input v-model="form.city" placeholder="请输入城市" />
        </el-form-item>
        <el-form-item label="区县" prop="area">
          <el-input v-model="form.area" placeholder="请输入区县" />
        </el-form-item>
        <el-form-item label="详细地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item label="最后登录时间" prop="lastLoginTime">
          <el-date-picker clearable size="small"
            v-model="form.lastLoginTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择最后登录时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="生日" prop="birth">
          <el-input v-model="form.birth" placeholder="请输入生日" />
        </el-form-item>
        <el-form-item label="邀请码" prop="inviteCode">
          <el-input v-model="form.inviteCode" placeholder="请输入邀请码" />
        </el-form-item>
        <el-form-item label="今日是否签到" prop="isSign">
          <el-input v-model="form.isSign" placeholder="请输入今日是否签到" />
        </el-form-item>
        <el-form-item label="用户状态">
          <el-radio-group v-model="form.status">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="经验值" prop="exp">
          <el-input v-model="form.exp" placeholder="请输入经验值" />
        </el-form-item>
        <el-form-item label="身份证号码" prop="idcard">
          <el-input v-model="form.idcard" placeholder="请输入身份证号码" />
        </el-form-item>
        <el-form-item label="我的签名" prop="sign">
          <el-input v-model="form.sign" placeholder="请输入我的签名" />
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
import { listAppUser, getAppUser, delAppUser, addAppUser, updateAppUser, exportAppUser } from "@/api/app/appUser";

export default {
  name: "AppUser",
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
      // app用户表格数据
      appUserList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        nickname: undefined,        password: undefined,        name: undefined,        socialUid: undefined,        socialToken: undefined,        mobile: undefined,        email: undefined,        clientId: undefined,        pushToken: undefined,        sex: undefined,        source: undefined,        socialSource: undefined,        avatar: undefined,        province: undefined,        city: undefined,        area: undefined,        address: undefined,        lastLoginTime: undefined,        birth: undefined,        inviteCode: undefined,        isSign: undefined,        status: undefined,        exp: undefined,        idcard: undefined,        sign: undefined      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "主键不能为空", trigger: "blur" }
        ],        nickname: [
          { required: true, message: "用户名称不能为空", trigger: "blur" }
        ],        password: [
          { required: true, message: "密码不能为空", trigger: "blur" }
        ],        name: [
          { required: true, message: "真实姓名不能为空", trigger: "blur" }
        ],        socialUid: [
          { required: true, message: "第三方登录用户id不能为空", trigger: "blur" }
        ],        socialToken: [
          { required: true, message: "第三方登录令牌不能为空", trigger: "blur" }
        ],        mobile: [
          { required: true, message: "手机号码不能为空", trigger: "blur" }
        ],        email: [
          { required: true, message: "邮箱地址不能为空", trigger: "blur" }
        ],        clientId: [
          { required: true, message: "客户端唯一标识不能为空", trigger: "blur" }
        ],        pushToken: [
          { required: true, message: "推送的令牌不能为空", trigger: "blur" }
        ],        sex: [
          { required: true, message: "性别不能为空", trigger: "change" }
        ],        source: [
          { required: true, message: "用户注册来源不能为空", trigger: "blur" }
        ],        socialSource: [
          { required: true, message: "第三方登录来源不能为空", trigger: "blur" }
        ],        avatar: [
          { required: true, message: "头像不能为空", trigger: "blur" }
        ],        province: [
          { required: true, message: "省份不能为空", trigger: "blur" }
        ],        city: [
          { required: true, message: "城市不能为空", trigger: "blur" }
        ],        area: [
          { required: true, message: "区县不能为空", trigger: "blur" }
        ],        address: [
          { required: true, message: "详细地址不能为空", trigger: "blur" }
        ],        lastLoginTime: [
          { required: true, message: "最后登录时间不能为空", trigger: "blur" }
        ],        birth: [
          { required: true, message: "生日不能为空", trigger: "blur" }
        ],        inviteCode: [
          { required: true, message: "邀请码不能为空", trigger: "blur" }
        ],        isSign: [
          { required: true, message: "今日是否签到不能为空", trigger: "blur" }
        ],        status: [
          { required: true, message: "用户状态不能为空", trigger: "blur" }
        ],        exp: [
          { required: true, message: "经验值不能为空", trigger: "blur" }
        ],        idcard: [
          { required: true, message: "身份证号码不能为空", trigger: "blur" }
        ],        sign: [
          { required: true, message: "我的签名不能为空", trigger: "blur" }
        ]      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询app用户列表 */
    getList() {
      this.loading = true;
      listAppUser(this.queryParams).then(response => {
        this.appUserList = response.rows;
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
        id: undefined,        nickname: undefined,        password: undefined,        name: undefined,        socialUid: undefined,        socialToken: undefined,        mobile: undefined,        email: undefined,        clientId: undefined,        pushToken: undefined,        sex: undefined,        source: undefined,        socialSource: undefined,        avatar: undefined,        province: undefined,        city: undefined,        area: undefined,        address: undefined,        createTime: undefined,        updateTime: undefined,        lastLoginTime: undefined,        birth: undefined,        inviteCode: undefined,        isSign: undefined,        status: "0",        exp: undefined,        idcard: undefined,        sign: undefined      };
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
      this.title = "添加app用户";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getAppUser(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改app用户";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateAppUser(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addAppUser(this.form).then(response => {
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
        return delAppUser(ids);
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
        this.$confirm('是否确认导出所有app用户数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
        }).then(() => {
            this.exportLoading = true;
            return exportAppUser(queryParams);
        }).then(response => {
            this.download(response.msg);
            this.exportLoading = false;
        })
    }
  }
};
</script>
