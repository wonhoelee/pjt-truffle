<!--젠킨스확인용1720 -->
<template>
  <v-app id="app">
    <!-- navbar 위치 -->
    <router-view name="header" />

    <router-view />

    <router-view name="footer" />
  </v-app>
</template>
<script>
export default {
  data() {
    return {
      year: new Date().getFullYear(),
    };
  },
};
</script>
<style scoped>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
}
/* 
#nav {
  padding: 30px;
  position: fixed;
  text-align: center;
  z-index: 1;
} */

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
