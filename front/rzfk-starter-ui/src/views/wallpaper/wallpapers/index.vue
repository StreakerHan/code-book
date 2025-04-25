<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="图片链接" prop="url">
        <el-input
          v-model="queryParams.url"
          placeholder="请输入图片链接"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图片宽度" prop="width">
        <el-input
          v-model="queryParams.width"
          placeholder="请输入图片宽度"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图片高度" prop="height">
        <el-input
          v-model="queryParams.height"
          placeholder="请输入图片高度"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图片标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入图片标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下载次数" prop="downloads">
        <el-input
          v-model="queryParams.downloads"
          placeholder="请输入下载次数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图片类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择图片类型" clearable size="small">
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
          v-hasPermi="['wallpaper:wallpapers:add']"
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
          v-hasPermi="['wallpaper:wallpapers:edit']"
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
          v-hasPermi="['wallpaper:wallpapers:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wallpaper:wallpapers:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wallpapersList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" v-if="true"/>
      <el-table-column label="图片链接" align="center" prop="url" />
      <el-table-column label="图片宽度" align="center" prop="width" />
      <el-table-column label="图片高度" align="center" prop="height" />
      <el-table-column label="图片标题" align="center" prop="title" />
      <el-table-column label="下载次数" align="center" prop="downloads" />
      <el-table-column label="图片类型" align="center" prop="type" />
      <el-table-column label="主题" align="center" prop="topic" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wallpaper:wallpapers:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wallpaper:wallpapers:remove']"
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

    <!-- 添加或修改壁纸对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="主题" prop="topic">
           <el-select v-model="form.topic" placeholder="请选择主题">
            <el-option v-for="dict in type"
              :key="dict.id"
              :value="dict.title"
              :label="dict.title"/>
          </el-select>
        </el-form-item>
        <el-form-item label="图片链接" prop="url">
          <imageUpload v-model="form.url"/>
        </el-form-item>
        <el-form-item label="图片宽度" prop="width">
          <el-input v-model="form.width" placeholder="请输入图片宽度" />
        </el-form-item>
        <el-form-item label="图片高度" prop="height">
          <el-input v-model="form.height" placeholder="请输入图片高度" />
        </el-form-item>
        <el-form-item label="图片标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入图片标题" />
        </el-form-item>
        <el-form-item label="下载次数" prop="downloads">
          <el-input v-model="form.downloads" placeholder="请输入下载次数" />
        </el-form-item>
        <el-form-item label="图片类型" prop="type">
          <el-input v-model="form.type" placeholder="请输入图片类型" />
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
import { listWallpapers, getWallpapers, delWallpapers, addWallpapers, updateWallpapers, exportWallpapers } from "@/api/wallpaper/wallpapers";
import { listWallpapersTopic } from "@/api/wallpaper/wallpapersTopic"

export default {
  name: "Wallpapers",
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
      // 壁纸表格数据
      wallpapersList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        url: undefined,        width: undefined,        height: undefined,        title: undefined,        downloads: undefined,        type: undefined,topic: undefined      },
      // 表单参数
      form: {},
      type:[],
      // 表单校验
      rules: {
           }
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
      listWallpapersTopic({
        pageNum: 1,
        pageSize: 1000}).then(response => {
        this.type = response.rows;

        this.loading = false;
      });
    },
    /** 查询壁纸列表 */
    getList() {
      this.loading = true;
      listWallpapers(this.queryParams).then(response => {
        this.wallpapersList = response.rows;
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
        id: undefined,        createTime: undefined,        url: undefined,        width: undefined,        height: undefined,        title: undefined,        downloads: undefined,        type: undefined ,topic:undefined     };
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
      this.title = "添加壁纸";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const id = row.id || this.ids
      getWallpapers(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改壁纸";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateWallpapers(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addWallpapers(this.form).then(response => {
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
        return delWallpapers(ids);
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
        this.$confirm('是否确认导出所有壁纸数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
        }).then(() => {
            this.exportLoading = true;
            return exportWallpapers(queryParams);
        }).then(response => {
            this.download(response.msg);
            this.exportLoading = false;
        })
    }
  }
};
</script>
