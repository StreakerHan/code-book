<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-input
          v-model="queryParams.type"
          placeholder="请输入类型"
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
          v-hasPermi="['app:appPost:add']"
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
          v-hasPermi="['app:appPost:edit']"
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
          v-hasPermi="['app:appPost:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['app:appPost:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="appPostList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" v-if="true"/>
      <el-table-column label="标题" align="center" :show-overflow-tooltip="true" prop="title" />
      <el-table-column label="发布时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="内容" align="center" prop="content" /> -->
      <el-table-column label="封面" align="center" prop="img" >
        <template slot-scope="scope">
          <img :src="scope.row.img" style="height:50px"/>
        </template>
        </el-table-column>
      <el-table-column label="类型" align="center" prop="type" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="视频" align="center" :show-overflow-tooltip="true" prop="video" />
      <el-table-column label="链接" align="center" prop="link" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['app:appPost:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['app:appPost:remove']"
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

    <el-dialog :visible.sync="dialogVisible">
  <img width="100%" :src="dialogImageUrl" alt="">
</el-dialog>

    <!-- 添加或修改app文章对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="图片" prop="image">
        <MultImageUpload v-if="showUpload" :list="images"></MultImageUpload>
        </el-form-item>
        <el-form-item label="内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="封面">
          <imageUpload v-model="form.img"/>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select
            v-model="form.type"
            filterable
            allow-create
            default-first-option
            placeholder="请输入类型">
            <el-option label="推荐" value="推荐">
            </el-option>
            <el-option label="赛事" value="赛事">
            </el-option>
            <el-option label="车手" value="车手">
            </el-option>
          </el-select>
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
        <el-form-item label="视频">
          <imageUpload v-model="form.video"/>
        </el-form-item>
        <el-form-item label="链接" prop="link">
          <el-input v-model="form.link" placeholder="请输入链接" />
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
import { listAppPost, getAppPost, delAppPost, addAppPost, updateAppPost, exportAppPost } from "@/api/app/appPost";
import MultImageUpload from "../../../components/MultImageUpload/index.vue";
export default {
    name: "AppPost",
    data() {
        return {
            uploadImgUrl: process.env.VUE_APP_BASE_API + "/oss/upload",
            dialogImageUrl: "",
            dialogVisible: false,
            disabled: false,
            //图片列表
            images: [],
            showUpload:false,
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
            // app文章表格数据
            appPostList: [],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                title: undefined,
                content: undefined,
                type: undefined,
                status: undefined,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                id: [
                    { required: true, message: "链接不能为空", trigger: "blur" }
                ],
                title: [
                    { required: true, message: "标题不能为空", trigger: "blur" }
                ],
                type: [
                    { required: true, message: "类型不能为空", trigger: "blur" }
                ],
                status: [
                    { required: true, message: "状态不能为空", trigger: "blur" }
                ]
            }
        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询app文章列表 */
        getList() {
            this.loading = true;
            listAppPost(this.queryParams).then(response => {
                this.appPostList = response.rows;
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
                id: undefined,
                image: undefined,
                description: undefined,
                title: undefined,
                createTime: undefined,
                content: undefined,
                img: undefined,
                type: undefined,
                status: undefined,
                createBy: undefined,
                video: undefined,
                link: undefined
            };
            this.images = []
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
            this.ids = selection.map(item => item.id);
            this.single = selection.length !== 1;
            this.multiple = !selection.length;
        },
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.showUpload = true
            this.open = true;
            this.title = "添加app文章";
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
          let that = this
            this.loading = true;
            this.showUpload = false
            this.reset();
            that.images = []
            const id = row.id || this.ids;
            getAppPost(id).then(response => {
              if(response.data.image != null && response.data.image != ""){
                  that.images = response.data.image.split(",")
                  this.showUpload = true
                  console.log(that.images)
                }else{
                  this.showUpload = true
                }
                
                this.loading = false;
                this.form = response.data;
                this.open = true;
                this.title = "修改app文章";
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    this.buttonLoading = true;
                    if (this.form.id != null) {
                      if(this.images.length != 0){
                        this.form.image = this.images.join(",")
                      }
                        updateAppPost(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
                            this.open = false;
                            this.getList();
                        }).finally(() => {
                            this.buttonLoading = false;
                        });
                    }
                    else {
                      if(this.images.length != 0){
                        this.form.image = this.images.join(",")
                      }
                        addAppPost(this.form).then(response => {
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
            this.$modal.confirm("是否确认删除该数据项？").then(() => {
                this.loading = true;
                return delAppPost(ids);
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
            this.$confirm("是否确认导出所有app文章数据项?", "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(() => {
                this.exportLoading = true;
                return exportAppPost(queryParams);
            }).then(response => {
                this.download(response.msg);
                this.exportLoading = false;
            });
        }
    },
    components: { MultImageUpload }
};
</script>
