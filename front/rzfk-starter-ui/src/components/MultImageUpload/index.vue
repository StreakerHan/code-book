<template>
  <div class="component-upload-image" style="width:100%">
     <div style="width:100%;display:flex;width:100%;height: 150px">
      <div v-if="imgList.length>0" v-for="(item,index) in imgList" :key="index" class="image">
        <el-image :src="item" :style="`width:150px;height:150px;`" fit="fill"/>
        <div class="mask">
          <div class="actions">
            <span title="预览"  @click.stop="yulan(index)">
              <i class="el-icon-zoom-in" />
            </span>
            <span title="移除" @click.stop="removeImage(index)">
              <i class="el-icon-delete" />
            </span>
          </div>
        </div>
      </div>
      <el-upload
        :action="uploadImgUrl"
        list-type="picture-card"
        :on-success="handleUploadSuccess"
        :before-upload="handleBeforeUpload"
        :on-error="handleUploadError"
        name="file"
        :show-file-list="false"
      >
      <el-image :style="`width:150px;height:150px;`">
        <div slot="error" class="image-slot">
          <i class="el-icon-plus" />
        </div>
      </el-image>
    </el-upload>
     </div>
    <el-dialog :visible.sync="dialogVisible" title="预览" width="800" append-to-body>
      <img :src="src" style="display: block; max-width: 100%; margin: 0 auto;">
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";

export default {
  data() {
    return {
      src:'',
      imgList:this.list,
      dialogVisible: false,
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/oss/upload", // 上传的图片服务器地址
      // headers: {
      //   Authorization: "Bearer " + getToken(),
      // },
    };
  },
  props: {
    fileType:{
      type: String,
      default: "",
    },
    list:{
      type: Array,
      default: [],
    }
  },
  methods: {
    yulan(index){
      this.dialogVisible = true;
      this.src = this.imgList[index]
    },
    clearList(){
      this.imgList.length=0;
    },
    removeImage(index) {
      this.imgList.splice(index, 1);
    },
    handleUploadSuccess(res) {
      let object = {};
      object.sourceFile  = res.data;
      this.imgList.push(res.data);
      this.$emit('input',this.imgList);
      this.loading.close();
    },
    handleBeforeUpload() {
      this.loading = this.$loading({
        lock: true,
        text: "上传中",
        background: "rgba(0, 0, 0, 0.7)",
      });
    },
    handleUploadError() {
      this.$message({
        type: "error",
        message: "上传失败",
      });
      this.loading.close();
    },
  },
  watch: {},
};
</script>

<style scoped lang="scss">
.image {
  position: relative;
  .mask {
    opacity: 0;
    position: absolute;
    top: 0;
    width:150px;
    height:150px;
    background-color: rgba(0, 0, 0, 0.5);
    transition: all 0.3s;
    .actions{
      width:100%;
      height:100%;
      i{
          font-size: 30px;
          background: bisque
      }
    }
  }
  &:hover .mask {
    opacity: 1;
  }
}
</style>
