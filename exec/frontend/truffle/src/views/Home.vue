<template>
  <div>
    <div class="mcontainer projects">
      <h1>TRUFFLE</h1>
      <div class="detail">쇼핑에 재미와 신뢰를 더하다</div>
      <div class="overlay"></div>
      <!-- <v-btn @click="goGuide" class="mainbtn" elevation="2" large outlined text color="white">GUIDE</v-btn> -->
      <button @click="goMain" class="home-btn" type="submit">MAIN</button>
      <button @click="goGuide" class="home-btn" type="submit">GUIDE</button>
      <button @click="goSign" class="home-btn" type="submit">LOGIN</button>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    goGuide() {
      this.$router.push({ name: 'Guide' });
    },
    goMain() {
      this.$router.push({ name: 'Main' });
    },
    goTest() {
      this.$router.push({ name: 'Test' });
    },
    goSign() {
      this.$router.push({ name: 'Sign-In-Up' });
    },
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  /* box-sizing: border-box; */
}
.home-btn {
  /* display: block; */
  margin: 10px 0px;
  width: 10%;
  /* background: #80ff00; */
  background: rgba(255, 255, 255, 1);
  color: #383838;
  font-weight: 700;
  border: none;
  padding: 1rem;

  font-size: 1rem;
  text-transform: uppercase;
  letter-spacing: 10px;
  outline: 0;
  transform: translateY(600px);
  animation: 2s slideIn ease-in-out forwards 2s;
  z-index: 10;
  opacity: 0;
}
.home-btn:hover {
  /* display: block; */
  margin: 10px 0px;
  width: 10%;
  background: #80ff00;
  color: #383838;
  font-weight: 700;
  border: none;
  padding: 1rem;
  /* border-radius: 8px; */
  font-size: 1rem;
  text-transform: uppercase;
  letter-spacing: 10px;
  outline: 0;
  z-index: 10;
}
.mcontainer {
  min-height: 100vh;
  position: inherit;
  width: auto;
  margin: 0;
  display: flex;
  background-color: #80ff00;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
}

h1 {
  font-size: 15rem;
  color: #fff;
  transform: translateY(-600px);
  animation: 1s slideIn ease-in-out forwards 1s;
  z-index: 10;
  opacity: 0;
  letter-spacing: 20px;
  position: relative;
}
.detail {
  margin-top: 0;
  margin-bottom: 2rem;
  font-size: 1rem;
  font-weight: 900;
  color: #fff;
  transform: translateY(600px);
  animation: 2s slideIn ease-in-out forwards 2s;
  z-index: 10;
  opacity: 0;
  letter-spacing: 15px;
  position: relative;
}
.mainbtn {
  font-size: 1rem;
  color: #fff;
  transform: translateY(600px);
  animation: 1s slideIn ease-in-out forwards 1s;
  z-index: 10;
  opacity: 0;
  letter-spacing: 10px;
  position: relative;
}
.mainbtn:hover {
  font-size: 1rem;
  color: #fff;
  background-color: #2bbef2;
  transform: translateY(600px);
  animation: 1s slideIn ease-in-out forwards 1s;
  z-index: 10;
  opacity: 0;
  letter-spacing: 10px;
  position: relative;
}
/* h1::before {
  content: '';
  width: 0%;
  height: 76px;
  background-color: #0f21bd;
  position: absolute;
  bottom: -10px;
  animation: 1s underline ease-in-out forwards 1s;
  mix-blend-mode: screen;
} */

.overlay {
  position: absolute;
  width: 100%;
  top: 0;
  bottom: 0;
  opacity: 0;
  left: 0;
  right: 0;
  background: no-repeat center/100% url('../assets/img/boardmen.jpg');
  transform: scale(0.5);
  animation: 1.5s slideIn ease-in-out forwards, 1.5s skewBg ease-in-out;
}

/* .anim-typewriter {
  animation: typewriter 4s steps(40) 1s 1 normal both, blinkTextCursor 500ms steps(40) infinite normal;
} */

@keyframes typewriter {
  from {
    width: 0;
  }
  to {
    width: 16em;
  }
}

@keyframes blinkTextCursor {
  from {
    border-right-color: rgba(255, 255, 255, 0.75);
  }
  to {
    border-right-color: transparent;
  }
}
@keyframes skewBg {
  0% {
    transform: scale(0.5);
  }
  100% {
    transform: scale(1);
  }
}

@keyframes underline {
  100% {
    width: 100%;
  }
}

@keyframes slideIn {
  100% {
    transform: translateY(0px);
    opacity: 1;
  }
}
</style>
