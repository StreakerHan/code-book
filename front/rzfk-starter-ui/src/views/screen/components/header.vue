<template>
  <div class="title">
    <a class="home-btn" @click="goback">
      <svg-icon icon-class="home"/>管理后台
    </a>
    <h3>{{$store.state.settings.title}}</h3>
    <div class="time">{{parseTime(time)}}</div>
  </div>
</template>
<script>
export default {
  name: "screen-header",
  data() {
    return {
      time: new Date()
    }
  },
  mounted() {
    let _this = this; // 声明一个变量指向Vue实例this，保证作用域一致
    this.timer = setInterval(() => {
      _this.time = new Date(); // 修改数据date
    }, 1000)
  },
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer); // 在Vue实例销毁前，清除我们的定时器
    }
  },
  methods: {
    goback(e) {
      this.$router.push({ path: "/index" }).catch(()=>{});
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss">
.title {
  margin: -10px 0 10px 0;
  width: 100%;
  text-align: center;
  color: white;
  font-size: 30px;
  -webkit-mask-image: -webkit-gradient(linear, 0 0, 0 bottom, from(white), to(rgb(151, 227, 253))); 
}
.home-btn {
  position: absolute;
  top: 34px;
  left: 100px;
}
.time {
  position: absolute;
  top: 38px;
  right: 100px;
}
</style>