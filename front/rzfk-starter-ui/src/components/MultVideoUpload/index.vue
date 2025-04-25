<template>
  <div class="component-upload-image" style="width:100%">
    <div style="width:100%;display:flex;width:700px;height: 150px">
      <div v-if="videoList.length>0" v-for="(item,index) in videoList" :key="index" class="image">
        <!-- <el-image :src="item.sourceFile" :style="`width:150px;height:150px;`" fit="fill"/> -->
        <video :src="item.sourceFile" :style="`width:150px;height:150px;`"></video>
        <div class="mask">
          <div class="actions">
            <span title="预览" @click.stop="yulan(index)">
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
      <video :src="src" controls="controls" style="display: block; max-width: 100%; margin: 0 auto;"></video>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";

export default {
  data() {
    return {
      src:'',
      videoList:this.list,
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
  watch:{
      videoList(newold,oldval){
      }
  },
  methods: {
    clearList(){
     this.videoList.length=0;
    },
    yulan(index){
      this.dialogVisible = true;
      this.src = this.videoList[index].sourceFile
    },
    removeImage(index) {
      this.videoList.splice(index, 1);
    },
    handleUploadSuccess(res) {
      let object = {};
      object.fileType = this.fileType;
      object.sourceFile  = res.data;
      this.videoList.push(object);
      this.$emit('input',this.videoList);
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
};
</script>

<style scoped lang="scss">
.image {
  position: relative;
  .mask {
    opacity: 0;
    position: absolute;
    top: 0;
    width: 100%;
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
