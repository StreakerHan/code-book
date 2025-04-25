<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="主键" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入主键"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="书籍主键" prop="bookId">
        <el-input
          v-model="queryParams.bookId"
          placeholder="请输入书籍主键"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目录名称" prop="catalogueName">
        <el-input
          v-model="queryParams.catalogueName"
          placeholder="请输入目录名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目录顺序" prop="seq">
        <el-input
          v-model="queryParams.seq"
          placeholder="请输入目录顺序"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="内容链接" prop="href">
        <el-input
          v-model="queryParams.href"
          placeholder="请输入内容链接"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="父目录主键" prop="parentId">
        <el-input
          v-model="queryParams.parentId"
          placeholder="请输入父目录主键"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="级别" prop="level">
        <el-input
          v-model="queryParams.level"
          placeholder="请输入级别"
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
          v-hasPermi="['app:bookContent:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="bookContentList"
      row-key="id"
      default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="书籍主键" prop="bookId" />
      <el-table-column label="目录名称" align="center" prop="catalogueName" />
      <el-table-column label="目录顺序" align="center" prop="seq" />
      <el-table-column label="内容链接" align="center" prop="href" />
      <el-table-column label="内容" align="center" prop="content" />
      <el-table-column label="父目录主键" align="center" prop="parentId" />
      <el-table-column label="级别" align="center" prop="level" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['app:bookContent:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['app:bookContent:add']"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['app:bookContent:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改书籍内容对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="书籍主键" prop="bookId">
          <el-input v-model="form.bookId" placeholder="请输入书籍主键" />
        </el-form-item>
        <el-form-item label="目录名称" prop="catalogueName">
          <el-input v-model="form.catalogueName" placeholder="请输入目录名称" />
        </el-form-item>
        <el-form-item label="目录顺序" prop="seq">
          <el-input v-model="form.seq" placeholder="请输入目录顺序" />
        </el-form-item>
        <el-form-item label="内容链接" prop="href">
          <el-input v-model="form.href" placeholder="请输入内容链接" />
        </el-form-item>
        <el-form-item label="内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="父目录主键" prop="parentId">
          <treeselect v-model="form.parentId" :options="bookContentOptions" :normalizer="normalizer" placeholder="请选择父目录主键" />
        </el-form-item>
        <el-form-item label="级别" prop="level">
          <el-input v-model="form.level" placeholder="请输入级别" />
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
import { listBookContent, getBookContent, delBookContent, addBookContent, updateBookContent } from "@/api/app/bookContent";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "BookContent",
  components: {
    Treeselect
  },
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 书籍内容表格数据
      bookContentList: [],
      // 书籍内容树选项
      bookContentOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        id: null,        bookId: null,        catalogueName: null,        seq: null,        href: null,        content: null,        parentId: null,        level: null      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "主键不能为空", trigger: "blur" }
        ],        bookId: [
          { required: true, message: "书籍主键不能为空", trigger: "blur" }
        ],        catalogueName: [
          { required: true, message: "目录名称不能为空", trigger: "blur" }
        ],        seq: [
          { required: true, message: "目录顺序不能为空", trigger: "blur" }
        ],        href: [
          { required: true, message: "内容链接不能为空", trigger: "blur" }
        ],        content: [
          { required: true, message: "内容不能为空", trigger: "blur" }
        ],        parentId: [
          { required: true, message: "父目录主键不能为空", trigger: "blur" }
        ],        level: [
          { required: true, message: "级别不能为空", trigger: "blur" }
        ]      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询书籍内容列表 */
    getList() {
      this.loading = true;
      listBookContent(this.queryParams).then(response => {
        this.bookContentList = this.handleTree(response.data, "id", "parentId");
        this.loading = false;
      });
    },
    /** 转换书籍内容数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.catalogueName,
        children: node.children
      };
    },
	/** 查询书籍内容下拉树结构 */
    getTreeselect() {
      listBookContent().then(response => {
        this.bookContentOptions = [];
        const data = { id: 0, catalogueName: '顶级节点', children: [] };
        data.children = this.handleTree(response.data, "id", "parentId");
        this.bookContentOptions.push(data);
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
        id: null,        bookId: null,        catalogueName: null,        seq: null,        href: null,        content: null,        parentId: null,        level: null      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.id) {
        this.form.parentId = row.id;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加书籍内容";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
	  this.loading = true;
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.parentId = row.id;
      }
      getBookContent(row.id).then(response => {
	    this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改书籍内容";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
		  this.buttonLoading = true;
          if (this.form.id != null) {
            updateBookContent(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addBookContent(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除书籍内容编号为"' + row.id + '"的数据项？').then(() => {
        this.loading = true;
        return delBookContent(row.id);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).finally(() => {
        this.loading = false;
      });
    }
  }
};
</script>
