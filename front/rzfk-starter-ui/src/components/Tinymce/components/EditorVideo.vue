<template>
  <div class="upload-container">
    <el-button :style="{background:color,borderColor:color}" icon="el-icon-upload" size="mini" type="warning"
      @click=" dialogVisible=true">
      上传视频
    </el-button>
    <el-dialog :visible.sync="dialogVisible">
      <el-upload :file-list="fileList" :show-file-list="true" :on-remove="handleRemove"
        :on-success="handleSuccess" :before-upload="beforeUpload" class="editor-slide-upload" :action="uploadurl"
        :data="uploadParams" :on-progress="uploadVideoProcess">
        <el-button v-if="Object.keys(fileList).length == 0"  size="small" type="primary">
          点击上传视频
        </el-button>
        <video v-if="videoUrl" :src="videoUrl" class="avatar" />
      </el-upload>
      <!-- <el-progress v-if="progressFlag" :percentage="loadProgress"></el-progress> -->
      <el-button @click="dialogVisible = false">
        取消
      </el-button>
      <el-button type="primary" @click="handleSubmit">
        确认
      </el-button>
    </el-dialog>
  </div>
</template>

<script>
  // import { getToken } from 'api/qiniu'

  export default {
    name: 'EditorSlideUpload',
    props: {
      color: {
        type: String,
        default: '#1890ff'
      }
    },
    data() {
      return {
        uploadurl: process.env.VUE_APP_BASE_API + "/ruiyuan-business/api/oss/file/uploadFile",
        uploadParams: {
          bucketName: 'ruiyuan-site',
          filePath: 'ruiyuan-site'
        },
        dialogVisible: false,
        listObj: {},
        fileList: [],
        videoUrl:'',
        loadProgress: 0, // 动态显示进度条
        progressFlag: false, // 关闭进度条
      }
    },
    methods: {
      uploadVideoProcess(event, file, fileList) {
        this.progressFlag = true; // 显示进度条
        this.loadProgress = parseInt(event.percent); // 动态获取文件上传进度
        if (this.loadProgress >= 100) {
          this.loadProgress = 100
          setTimeout(() => {
            this.progressFlag = false
          }, 1000) // 一秒后关闭进度条
        }
      },
      checkAllSuccess() {
        return Object.keys(this.listObj).every(item => this.listObj[item].hasSuccess)
      },
      handleSubmit() {
        const arr = this.fileList[0]
        this.$emit('successCBK1', arr.url)
        this.listObj = {}
        this.fileList = []
        this.dialogVisible = false
      },
      handleSuccess(response, file) {
        const uid = file.uid
        this.fileList.push(file.response)
        this.videoUrl = file.response.url
        const objKeyArr = Object.keys(this.listObj)
        for (let i = 0, len = objKeyArr.length; i < len; i++) {
          if (this.listObj[objKeyArr[i]].uid === uid) {
            this.listObj[objKeyArr[i]].url = response.url
            this.listObj[objKeyArr[i]].hasSuccess = true
            return
          }
        }
      },
      handleRemove(file) {
        this.fileList = []
      },
      beforeUpload(file) {
        const _self = this
        const _URL = window.URL || window.webkitURL
        const fileName = file.uid
        this.listObj[fileName] = {}
        return new Promise((resolve, reject) => {
          const img = new Image()
          img.src = _URL.createObjectURL(file)
          img.onload = function () {
            _self.listObj[fileName] = {
              hasSuccess: false,
              uid: file.uid,
              width: this.width,
              height: this.height
            }
          }
          resolve(true)
        })
      }
    }
  }

</script>

<style lang="scss" scoped>
  .editor-slide-upload {
    margin-bottom: 20px;

    // /deep/ .el-upload--picture-card {
    //   width: 100%;
    // }
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
