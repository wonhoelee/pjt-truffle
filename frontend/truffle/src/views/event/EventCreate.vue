<template>
  <div>
    <v-container>
      <div style="padding:80px">
        <v-img :src="create" max-height="400" max-width="1900" class="mt-12 mb-0"></v-img>
        <form v-on:submit.prevent="writeContent">
          <v-text-field label="제품명" v-model="product"></v-text-field>
          <!-- 썸네일 -->
          <div class="formdata">
            <v-file-input
              id="thumbnail"
              name="thumbnail"
              v-model="image"
              show-size
              label="썸네일 이미지 (입력창을 클릭해주세요)"
              @change="Preview_image($event)"
              style="display:inline-block; cursor : pointer;"
            ></v-file-input>
            <v-img :src="url" id="preview"></v-img>
          </div>
          <v-select :items="items" v-model="category" label="카테고리" dense solo></v-select>
          <div class="input-container gender">
            <label for="">GENDER</label>
            <div class="wrapper">
              <input type="radio" name="select" id="option-1" value="1" v-model="gender" />
              <input type="radio" name="select" id="option-2" value="2" v-model="gender" />
              <label for="option-1" class="option option-1">
                <div class="dot"></div>
                <span>남성</span>
              </label>
              <label for="option-2" class="option option-2">
                <div class="dot"></div>
                <span>여성</span>
              </label>
            </div>
          </div>
          <div class="input-container age">
            <label for="">AGE</label>
            <div class="wrapper1">
              <input type="radio" name="select1" id="gender-option-1" value="10" v-model="age" />
              <input type="radio" name="select1" id="gender-option-2" value="20" v-model="age" />
              <input type="radio" name="select1" id="gender-option-3" value="30" v-model="age" />

              <label for="gender-option-1" class="gender-option gender-option-1">
                <div class="dot"></div>
                <span>10대</span>
              </label>
              <label for="gender-option-2" class="gender-option gender-option-2">
                <div class="dot"></div>
                <span>20대</span>
              </label>
              <label for="gender-option-3" class="gender-option gender-option-3">
                <div class="dot"></div>
                <span>30대</span>
              </label>
            </div>
            <div class="wrapper1">
              <input type="radio" name="select1" id="gender-option-4" value="40" v-model="age" />
              <input type="radio" name="select1" id="gender-option-5" value="50" v-model="age" />
              <input type="radio" name="select1" id="gender-option-6" value="60" v-model="age" />
              <label for="gender-option-4" class="gender-option gender-option-4">
                <div class="dot"></div>
                <span>40대</span>
              </label>
              <label for="gender-option-5" class="gender-option gender-option-5">
                <div class="dot"></div>
                <span>50대 이상</span>
              </label>
              <label for="gender-option-6" class="gender-option gender-option-6">
                <div class="dot"></div>
                <span>60대 이상</span>
              </label>
            </div>
          </div>
          <!-- 가격 -->
          <div class="input-container gender">
            <label for="">가격</label>
            <input type="text" v-model="price" @keyup="priceComma" id="price" />
          </div>
          <div class="input-container gender">
            <label for="">당첨자수</label>
            <input type="text" v-model="win_num" />
          </div>
          <v-container>
            <v-row>
              <v-col cols="12" lg="6">
                <v-menu ref="menu1" v-model="menu1" :close-on-content-click="false" transition="scale-transition" offset-y max-width="290px" min-width="auto">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field v-model="open_date" label="시작일" persistent-hint prepend-icon="mdi-calendar" v-bind="attrs" @blur="date = parseDate(open_date)" v-on="on"></v-text-field>
                  </template>
                  <v-date-picker v-model="open_date" @input="menu1 = false"></v-date-picker>
                </v-menu>
              </v-col>

              <v-col cols="12" lg="6">
                <v-menu v-model="menu2" :close-on-content-click="false" transition="scale-transition" offset-y max-width="290px" min-width="auto">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field v-model="end_date" label="마감일" persistent-hint prepend-icon="mdi-calendar" readonly v-bind="attrs" v-on="on"></v-text-field>
                  </template>
                  <v-date-picker v-model="end_date" @input="menu2 = false"></v-date-picker>
                </v-menu>
              </v-col>
            </v-row>
          </v-container>
          <div id="summernote"></div>
        </form>

        <div class="btn-right">
          <v-btn color="#000" dark type="submit" @click="eventInsert">
            등록
          </v-btn>
          <v-btn color="#000" class="ml-1" dark type="button" @click="$router.go(-1)">
            뒤로가기
          </v-btn>
        </div>
      </div>
    </v-container>
  </div>
</template>

<script>
import { eventInsert } from '@/api/event';
import { retailerAllEvent } from '@/api/auth';

export default {
  data: (vm) => ({
    date: new Date().toISOString().substr(0, 10),
    dateFormatted: vm.formatDate(new Date().toISOString().substr(0, 10)),
    menu1: false,
    menu2: false,

    age: '',
    items: ['의류', '뷰티', '잡화', '신발', '식품', '디지털', '취미/문화', '기타'],
    category: '',
    detail: $('#summernote').summernote('code'),
    end_date: '',
    gender: '',
    join_num: '',
    open_date: '',
    price: '',
    product: '',
    win_num: '',
    create: require('@/assets/img/create.jpg'),
    url: null,
    image: null,
  }),
  mounted() {
    $('#summernote').summernote({
      height: 300,
      minHeight: null,
      maxHeight: null,
      focus: true,
      tabsize: 2,
      toolbar: [
        ['style', ['style']],
        ['font', ['bold', 'underline', 'clear']],
        ['color', ['color']],
        ['para', ['ul', 'ol', 'paragraph']],
        ['table', ['table']],
        ['insert', ['link', 'picture', 'video']],
        ['view', ['fullscreen', 'codeview', 'help']],
      ],
    });
  },
  computed: {
    computedDateFormatted() {
      return this.formatDate(this.date);
    },
  },
  watch: {
    date(val) {
      this.dateFormatted = this.formatDate(this.date);
    },
    price() {
      const price = this.price;
      // console.log(typeof price);
      this.price = price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
      // this.price = price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    },
  },

  methods: {
    priceComma() {
      var selection = window.getSelection().toString();
      if (selection !== '') {
        return;
      } else {
        var input = this.price;
        var input = input.replace(/[\D\s\._\-]+/g, '');
        input = Number(input);
        this.price = input.toLocaleString('en-US');
      }
    },
    Preview_image() {
      this.url = URL.createObjectURL(this.image);
    },
    formatDate(date) {
      if (!date) return null;

      const [year, month, day] = date.split('-');
      return `${year}-${month}-${day}`;
    },
    parseDate(date) {
      if (!date) return null;

      const [month, day, year] = date.split('/');
      return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;
    },
    async eventInsert() {
      const uuid = this.$store.state.uuid;
      var frm = new FormData();
      frm.append('썸네일이미지', this.image);
      frm.append('uuid', this.$store.state.uuid);
      frm.append('age', this.age);
      frm.append('category', this.category);
      // frm.append('image', this.image);
      frm.append('detail', $('#summernote').summernote('code'));
      frm.append('open_date', this.open_date);
      frm.append('end_date', this.end_date);
      frm.append('gender', this.gender);
      frm.append('price', this.price.replace(/[\D\s\._\-]+/g, ''));
      frm.append('product', this.product);
      frm.append('win_num', this.win_num);
      for (var key of frm.keys()) {
        // console.log(key);
      }
      const response = await eventInsert(frm);
      // console.log(response);
      const { data } = await retailerAllEvent(uuid);
      // console.log(data[data.length - 1]);
      var event_id = data[data.length - 1].event_id;
      this.$router.push({ name: 'EventDetail', query: { event_id: event_id } });
    },
  },
};
</script>

<style scoped>
.kor {
  font-family: 'Nanum Gothic', sans-serif;
  margin-bottom: 1rem;
}
.btn-right {
  text-align: right;
  margin-top: 2rem;
}
:root {
  font-size: 100%;
  font-size: 16px;
  line-height: 1.5;
}
.body {
  padding: 0;
  margin: 0;
  font-family: 'Montserrat', sans-serif;
  font-weight: 500;
}
h1 {
  font-size: 2.25rem;
  font-weight: 700;
}
h2 {
  font-size: 1.5rem;
  font-weight: 700;
}
a:link {
  text-decoration: none;
  color: #07b8ac;
}
a:hover {
  text-decoration: underline;
}
.small {
  font-size: 80%;
  text-align: center;
}
.split-screen {
  display: flex;
  flex-direction: column;
}
.left {
  height: 100%;
  margin-top: 10rem;
}
.right {
  margin-top: 15rem;
}
.left,
.right {
  display: flex;
  justify-content: center;
  align-items: center;
}
.left {
  background: linear-gradient(rgba(0, 0, 0, 0), rgba(0, 0, 0, 0.5)), url('../../assets/img/hero.jpg');
  background-size: cover;
  height: 100%;
}
.left .copy {
  color: white;
  text-align: center;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
.left p {
  font-weight: 400;
}
.right .copy {
  color: black;
  text-align: center;
}
.right .copy p {
  margin: 1.5em 0;
  font-size: 0.875rem;
}
.right form {
  width: 400px;
}
form input[type='text'],
form input[type='email'],
form input[type='password'] {
  display: block;
  width: 100%;
  box-sizing: border-box;
  border-radius: 8px;
  border: 1px solid #c4c4c4;
  padding: 1em;
  margin-bottom: 1.25rem;
  font-size: 0.875rem;
}
label {
  display: block;
  margin-bottom: 0.5rem;
  font-size: 0.75rem;
}
.input-container.password {
  position: relative;
}
.input-container.password i {
  position: absolute;
  top: 42px;
  right: 16px;
}

.checkbox-container input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
}
.checkbox-container {
  display: block;
  position: relative;
  padding-left: 2rem;
  cursor: pointer;
  user-select: none;
  margin: 0 0 1.25rem 0;
}
.checkbox-container input:checked ~ .checkmark {
  background-color: #2c4893;
  border: 1px solid #2c4893;
}
.checkmark {
  position: absolute;
  width: 24px;
  height: 24px;
  background: white;
  border: 1px solid #c4c4c4;
  border-radius: 2px;
  top: -4px;
  left: 0;
}
.checkmark:after {
  content: '';
  position: absolute;
  display: none;
}
.checkbox-container input:checked ~ .checkmark::after {
  display: block;
}
.checkbox-container .checkmark:after {
  border: solid white;
  border-width: 0 3px 3px 0;
  width: 5px;
  height: 10px;
  left: 8px;
  top: 4px;
  transform: rotate(45deg);
}
.signup-btn {
  display: block;
  margin: 10px 0px;
  width: 100%;
  background: #f3118e;
  color: white;
  font-weight: 700;
  border: none;
  padding: 1rem;
  border-radius: 8px;
  font-size: 0.75rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}
.signup-btn:hover {
  backdrop-filter: #2c4893;
  cursor: pointer;
}
.gender-age {
  display: flex;
  align-items: center;
}

@media screen and (min-width: 900px) {
  .split-screen {
    flex-direction: row;
    height: 20vh;
  }
  .left,
  .right {
    display: flex;
    height: 100vh;
    width: 50%;
  }
}

.search {
  margin: 0px 0px 20px 3px;
}
.wrapper {
  display: inline-flex;
  background: #fff;
  height: 50px;
  width: 250px;
  align-items: center;
  justify-content: space-evenly;
  border-radius: 5px;
  padding: 0 0 10px 0;
  /* box-shadow: 5px 5px 30px rgba(0, 0, 0, 0.2); */
}
.wrapper .option {
  background: #fff;
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  margin: 0 15px 0 0;
  border-radius: 5px;
  cursor: pointer;
  padding: 0 10px;
  border: 2px solid lightgrey;
  transition: all 0.3s ease;
}
.wrapper .option .dot {
  height: 20px;
  width: 20px;
  background: #d9d9d9;
  border-radius: 50%;
  position: relative;
}
.wrapper .option .dot::before {
  position: absolute;
  content: '';
  top: 4px;
  left: 4px;
  width: 12px;
  height: 12px;
  background: #2bbef2;
  border-radius: 50%;
  opacity: 0;
  transform: scale(1.5);
  transition: all 0.3s ease;
}
input[type='radio'] {
  display: none;
}
/* 성별 */
#option-1:checked:checked ~ .option-1,
#option-2:checked:checked ~ .option-2 {
  border-color: #2bbef2;
  background: #2bbef2;
}
#option-1:checked:checked ~ .option-1 .dot,
#option-2:checked:checked ~ .option-2 .dot {
  background: #fff;
}
#option-1:checked:checked ~ .option-1 .dot::before,
#option-2:checked:checked ~ .option-2 .dot::before {
  opacity: 1;
  transform: scale(1);
}
.wrapper .option span {
  font-size: 15px;
  color: #808080;
}
#option-1:checked:checked ~ .option-1 span,
#option-2:checked:checked ~ .option-2 span {
  color: #fff;
}

/* 연령대 */

.wrapper1 {
  display: flex;
  background: #fff;
  height: 50px;
  width: 400px;
  align-items: center;
  justify-content: space-evenly;
  border-radius: 5px;
  padding: 0 0 10px 0;
  /* box-shadow: 5px 5px 30px rgba(0, 0, 0, 0.2); */
}
.wrapper1 .gender-option {
  background: #fff;
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  margin: 0 15px 0 0;
  border-radius: 5px;
  cursor: pointer;
  padding: 0 10px;
  border: 2px solid lightgrey;
  transition: all 0.3s ease;
}
.wrapper1 .gender-option .dot {
  height: 20px;
  width: 20px;
  background: #d9d9d9;
  border-radius: 50%;
  position: relative;
}
.wrapper1 .gender-option .dot::before {
  position: absolute;
  content: '';
  top: 4px;
  left: 4px;
  width: 12px;
  height: 12px;
  background: #2bbef2;
  border-radius: 50%;
  opacity: 0;
  transform: scale(1.5);
  transition: all 0.3s ease;
}
input[type='radio'] {
  display: none;
}

#gender-option-1:checked:checked ~ .gender-option-1,
#gender-option-2:checked:checked ~ .gender-option-2,
#gender-option-3:checked:checked ~ .gender-option-3,
#gender-option-4:checked:checked ~ .gender-option-4,
#gender-option-5:checked:checked ~ .gender-option-5,
#gender-option-6:checked:checked ~ .gender-option-6 {
  border-color: #2bbef2;
  background: #2bbef2;
}
#gender-option-1:checked:checked ~ .gender-option-1 .dot,
#gender-option-2:checked:checked ~ .gender-option-2 .dot,
#gender-option-3:checked:checked ~ .gender-option-3 .dot,
#gender-option-4:checked:checked ~ .gender-option-4 .dot,
#gender-option-5:checked:checked ~ .gender-option-5 .dot,
#gender-option-6:checked:checked ~ .gender-option-6 .dot {
  background: #fff;
}
#gender-option-1:checked:checked ~ .gender-option-1 .dot::before,
#gender-option-2:checked:checked ~ .gender-option-2 .dot::before,
#gender-option-3:checked:checked ~ .gender-option-3 .dot::before,
#gender-option-4:checked:checked ~ .gender-option-4 .dot::before,
#gender-option-5:checked:checked ~ .gender-option-5 .dot::before,
#gender-option-6:checked:checked ~ .gender-option-6 .dot::before {
  opacity: 1;
  transform: scale(1);
}
.wrapper1 .option span {
  font-size: 17px;
  color: #808080;
}

#gender-option-1:checked:checked ~ .gender-option-1 span,
#gender-option-2:checked:checked ~ .gender-option-2 span,
#gender-option-3:checked:checked ~ .gender-option-3 span,
#gender-option-4:checked:checked ~ .gender-option-4 span,
#gender-option-5:checked:checked ~ .gender-option-5 span,
#gender-option-6:checked:checked ~ .gender-option-6 span {
  color: #fff;
}

#preview {
  max-height: 200px;
  max-width: 200px;
  display: inline-block;
  margin-left: 5%;
}
.v-file-input {
  width: 300px;
}
</style>
