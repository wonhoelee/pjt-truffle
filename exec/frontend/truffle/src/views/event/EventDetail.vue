<template>
  <div class="eventdetail">
    <div class="card-wrapper">
      <div class="card">
        <!-- card left -->
        <div class="product-imgs">
          <div class="img-display">
            <div class="img-showcase">
              <!-- 이미지 -->
              <img class="detail-image" :src="imgURL + event.event_id" />
            </div>
          </div>
        </div>

        <!-- card right -->
        <div class="product-content">
          <h2 class="product-title">{{ event.product }}</h2>

          <div class="product-price">
            <p class="price">
              PRICE:
              <!-- <span>{{ event.price }}</span> -->
              <span>{{ priceComma }}원</span>
            </p>
          </div>
          <div class="tag">
            <p class="product-tag">
              {{ gender }}
            </p>
            <br />
            <p class="product-tag">{{ event.age }}대</p>
          </div>
          <div class="product-detail">
            <!-- <h2>about this item:</h2> -->
            <ul>
              <li>
                CATEGORY:
                <span>{{ event.category }}</span>
              </li>
              <li>
                응모자수:
                <span>{{ event.join_num }}명</span>
              </li>

              <li>
                당첨자수:
                <span>{{ event.win_num }}명</span>
              </li>
              <li>
                마감일:
                <span>{{ event.end_date }}</span>
              </li>
            </ul>
          </div>

          <v-col cols="auto" v-show="this.$store.state.type == '1' && new Date(this.event.end_date) > Date.now()">
            <div class="join-info" style="" v-if="cancelcheck">
              <button type="button" id="joinBtn" class="btn" value="응모하기" @click="joinAdd">
                응모하기
              </button>
            </div>
            <v-dialog transition="dialog-top-transition" max-width="600" v-else>
              <template class="join-info" v-slot:activator="{ on, attrs }">
                <div class="join-info" @click="joinAdd">
                  <button class="btn" v-bind="attrs" id="btn-join" v-on="on">응모취소</button>
                </div>
              </template>
              <template v-slot:default="dialog" v-show="cancelcheck == true">
                <v-card>
                  <v-toolbar color="dark" dark>응모취소확인</v-toolbar>
                  <v-card-text>
                    <div class="text-h5 text-center pt-10">
                      응모를 취소하시겠습니까?
                    </div>
                  </v-card-text>
                  <v-card-actions class="justify-end">
                    <v-btn text @click="cancel">OK</v-btn>
                    <v-btn text @click="dialog.value = false">닫기</v-btn>
                  </v-card-actions>
                </v-card>
              </template>
            </v-dialog>
          </v-col>
          <!-- 추첨 -->
          <div v-show="this.$store.state.uuid == this.event.uuid && new Date(this.event.end_date) < Date.now()" class="join-info">
            <button type="button" class="btn" id="winnerbtn" @click="raffleGo">
              추첨하기
            </button>
          </div>

          <!-- <div v-show="new Date(this.event.end_date) < Date.now()" class="join-info" @click="winnerListGo">
            <button type="button" class="btn">
              당첨자보기
            </button>
          </div> -->
          <v-col cols="auto" v-show="winnerChk == true && new Date(this.event.end_date) < Date.now()">
            <v-dialog transition="dialog-top-transition" max-width="600">
              <template v-slot:activator="{ on, attrs }">
                <v-btn class="ma-2 white--text" block color="#000" largedepressed v-bind="attrs" @click="winnerListShow" v-on="on">
                  당첨자보기
                </v-btn>
              </template>
              <template v-slot:default="dialog">
                <v-card>
                  <v-toolbar color="dark" dark class="text-h5" style="display:flex; justify-content:center;">당첨자내역</v-toolbar>
                  <v-card-text>
                    <div class="text-h6 pa-2" style="display:flex; justify-content:center;" v-for="(win, index) in modal" :key="index">
                      <p id="win_email">{{ win }}</p>
                    </div>
                  </v-card-text>
                  <v-card-actions class="justify-end">
                    <v-btn text @click="dialog.value = false">Close</v-btn>
                  </v-card-actions>
                </v-card>
              </template>
            </v-dialog>
          </v-col>
          <div v-if="this.$store.state.type == 1">
            <div class="join-info">
              <div v-if="paycheck == true && new Date(this.event.end_date) < Date.now()">
                <button type="button" class="btn" id="payment" @click="iamport">
                  결제하기
                </button>
              </div>
              <div v-else>
                <v-dialog transition="dialog-top-transition" max-width="600">
                  <template class="join-info" v-slot:activator="{ on, attrs }">
                    <div class="join-info">
                      <button class="btn" v-bind="attrs" id="btn-join" v-on="on" value="응모하기">결제취소</button>
                    </div>
                  </template>
                  <template v-slot:default="dialog" v-show="cancelcheck == true">
                    <v-card>
                      <v-toolbar color="dark" dark>결제취소확인</v-toolbar>
                      <v-card-text>
                        <div class="text-h5 text-center pt-10">
                          결제를 취소하시겠습니까?
                        </div>
                      </v-card-text>
                      <v-card-actions class="justify-end">
                        <v-btn text @click="cancleiamport">OK</v-btn>
                        <v-btn text @click="dialog.value = false">닫기</v-btn>
                      </v-card-actions>
                    </v-card>
                  </template>
                </v-dialog>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <v-container>
      <!-- <EventDetailTab :event_id="event_id"></EventDetailTab> -->
      <div>
        <div class="tab-container">
          <div class="tabs">
            <label class="tab tab01" @click="tab01()">
              <p id="tab1-title">DETAIL</p>
            </label>
            <span class="border"></span>
            <span class="background"></span>
          </div>
        </div>

        <div class="detail-contain">
          <div v-html="event.detail">
            {{ event.detail }}
          </div>
        </div>
      </div>
      <div class="btns">
        <div v-if="this.$store.state.uuid == event.uuid" style="text-align:right">
          <v-btn color="" class="mr-1" dark @click="updateGo">수정</v-btn>
          <v-btn dark @click="$router.go(-1)">뒤로가기</v-btn>
        </div>
        <!-- else -->
        <div v-else style="text-align:right">
          <v-btn dark @click="$router.go(-1)">뒤로가기</v-btn>
        </div>
      </div>
    </v-container>
  </div>
</template>

<script>
import EventDetailTab from '@/views/event/EventDetailTab';
import { eventDetail, eventJoin, checkPartipants, createPartipants, selectedWinner, createWinner } from '@/api/event';
import { fetchUser, cancelPart } from '@/api/auth';
import { verifyIamport, completePayment, fetchOrder, cancleOrder, deleteOrdertable } from '@/api/order';

export default {
  name: 'EventDetail',
  components: { EventDetailTab },
  data() {
    return {
      event: '',
      tabcheck: false,
      gender: '',
      joincheck: false,
      event_id: this.$route.query.event_id,
      showWinner: false,
      winnerList: [],
      modal: [],
      MaskingEmail: '',
      emailLen: '',
      dialog: true,
      detailImg: '',
      partcheck: false,
      cancelcheck: true,
      winnerEventId: [],
      paycheck: true,
      imgURL: 'https://j4d110.p.ssafy.io/truffle/event/selectEventImgFileEventID?event_id=',
      //로드늦어서 따로 변수지정함
      event_id: this.$route.query.event_id,
      uuid: this.$store.state.uuid,
      beforePart: false,
      uuidList: [],
      raffleList: [],
      winnerChk: false,
    };
  },
  computed: {
    priceComma: function() {
      if (this.event.price) {
        return this.event.price.toLocaleString('ko-KR');
      }
    },
  },
  async created() {
    const event_id = this.$route.query.event_id;
    // console.log('이벤트아이디', event_id);
    const { data } = await eventDetail(event_id);

    this.event = data[0];
    if (this.event.gender == 1) {
      this.gender = '남자';
    } else {
      this.gender = '여자';
    }
    const part_data = await checkPartipants(event_id);
    console.log(part_data);
    console.log('uuid:', this.$store.state.uuid);
    const win_data = await selectedWinner(event_id);

    for (let i = 0; i < part_data.data.length; i++) {
      if (this.$store.state.uuid == part_data.data[i].uuid) {
        const target = document.getElementById('joinBtn');
        // this.beforePart = true;
        target.innerText = '응모취소';
        // target.style.backgroundColor = '#000';
        // console.log('joinBtn', joinBtn);
        break;
      }

      if (this.event.uuid == this.$store.state.uuid && win_data.data.length != 0) {
        const winnerbtn = document.getElementById('winnerbtn');
        winnerbtn.innerText = '추첨완료';
      }
      //응모한 사람인지 확인(응모취소 버튼보이게)
    }
    //당첨자보기
    const winnerData = await selectedWinner(event_id);
    if (winnerData.data.length != 0) {
      this.winnerChk = true;
    }

    //당첨자 불러오기
    const response = await selectedWinner(this.event.event_id);
    if (response.data.length != 0) {
      const target = document.getElementById('winnerbtn');
      target.disabled = true;
      // target.innerText = '추첨완료';
      // console.log('당첨여부', response.data);
      for (let i = 0; i < response.data.length; i++) {
        this.winnerEventId.push(response.data[i].event_id);
      }
      //결제하기 버튼 보일지말지 확인
      if (this.winnerEventId.includes(this.event.uuid)) {
        this.paycheck = true;
      }
    }

    //결제여부
    // console.log(event_id);
    const res = await fetchOrder(event_id);
    // console.log('결제여부', res);
    for (let i = 0; i < res.data.length; i++) {
      if (this.$store.state.uuid == res.data[i].uuid) {
        this.paycheck = false;
      }
    }
  },
  methods: {
    getRandomIntInclusive(min, max) {
      const randomBuffer = new Uint32Array(1);
      window.crypto.getRandomValues(randomBuffer);
      let randomNumber = randomBuffer[0] / (0xffffffff + 1);
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(randomNumber * (max - min + 1)) + min;
      // console.log(Math.floor(randomNumber * (max - min + 1) + min));
    },
    async raffleGo() {
      if (this.showWinner == false && this.event.join_num >= this.event.win_num) {
        const { data } = await checkPartipants(this.event.event_id);
        this.event.win_num;

        while (data.length >= Number(this.event.win_num)) {
          var moveNum = data.splice(Math.floor(Math.random() * data.length), 1)[0];
          this.raffleList.push(moveNum);
          if (this.raffleList.length == this.event.win_num) {
            break;
          }
        }

        const target = document.getElementById('winnerbtn');
        // target.disabled = true;
        target.innerText = '추첨완료';
        // console.log(this.winnerList);
        // console.log('당첨자', this.winnerList);

        this.winnerListGo();
      } else if (this.event.join_num < this.event.win_num) {
        this.$swal({
          icon: 'info',
          title: '응모자가 없습니다',
          showConfirmButton: false,
          timer: 1500,
        });
      } else {
        this.$swal({
          icon: 'info',
          title: '추첨하였습니다 ',
          showConfirmButton: false,
          timer: 1500,
        });
      }
    },
    async winnerListGo() {
      console.log(this.raffleList);
      for (let i = 0; i < this.raffleList.length; i++) {
        const winData = {
          uuid: this.raffleList[i].uuid,
          event_id: this.event.event_id,
        };
        const { data } = await createWinner(winData);
        console.log(data, '당첨자확인');
        this.showWinner = true;
        this.winnerChk = true;
      }
    },
    async winnerListShow() {
      //modal에 데이타 있으면
      if (this.modal) {
        this.modal = [];
      }
      const { data } = await selectedWinner(this.event.event_id);
      // console.log('winnerListShow', data);
      // this.modal = data;
      for (let i = 0; i < data.length; i++) {
        var len = data[i].email.split('@')[0].length - 4;
        this.modal.push(data[i].email.replace(new RegExp('.(?=.{0,' + len + '}@)', 'g'), '*'));
      }
      // console.log('타입', this.modal);
    },
    //응모하기버튼누르면
    async joinAdd() {
      const email = this.$store.state.email;
      const event_id = this.$route.query.event_id;
      // console.log(event_id, email);
      // 로그인한 유저가 참여한적이 있는지 체크
      const { data } = await checkPartipants(event_id);
      // console.log(data);
      for (let i = 0; i < data.length; i++) {
        //참여한적이 있으면
        if (data[i].email == email) {
          this.partcheck = true;
          //중단
          break;
        } else {
          //for문끝까지 돌아야하므로 continue처리
          continue;
        }
      }
      //참여한적이 없다 (check==false 초기값으로되어있음)
      if (this.partcheck == false) {
        const { data } = await eventJoin(event_id);
        if (data == 'SUCCESS') {
          // console.log('1증가');
          const partData = {
            event_id: this.$route.query.event_id,
            uuid: this.$store.state.uuid,
          };
          //참여자테이블에 추가
          const part_data = await createPartipants(partData);
          // console.log('part_Data', part_data);
          if (part_data.data == 'SUCCESS') {
            // console.log('성공');
            const target = document.getElementById('btn-join');
            // console.log(target);

            this.cancelcheck = false;
          }
          const { data } = await eventDetail(event_id);
          this.event.join_num = data[0].join_num;
        }
      } else {
        this.cancelcheck = false;
        // console.log('이미참여한이벤트');
      }
    },
    async cancel() {
      const { data } = await cancelPart(this.event_id, this.uuid);
      console.log('취소후', data);
      const resp = await eventDetail(this.event_id);
      this.event.join_num = resp.data[0].join_num;
      this.cancelcheck = true;
      this.dialog = false;
    },

    updateGo() {
      var event_id = this.event.event_id;
      this.$router.push({ name: 'EventUpdate', query: { event_id: event_id } });
    },

    //
    async iamport() {
      var event_id = this.event.event_id;
      const { data } = await eventDetail(event_id);
      var event = data[0];
      // console.log(event.price);

      // 현재이벤트 상태가 결제됬는지 안됬는지 판단하는 API 추후 구현 예정
      // 해당 API로 0 반환시(미결제이벤트) 결제진행, else: 결제진행 X

      //가맹점 식별코드
      const response = await fetchUser(this.$store.state.email);
      // console.log(response);
      Vue.IMP().request_pay(
        {
          pg: 'inicis',
          pay_method: 'card',
          merchant_uid: event.event_id + new Date().getTime(), // 이벤트 아이디 매핑, 결제완료시 재결제 불가능하므로 시간난수 추가(테스트용)
          name: event.product, // 이벤트 제품이름 매핑
          amount: 100, // 이벤트 가격 매핑
          buyer_email: response.data.email, // 로그인한유저의 이메일 넣기
          buyer_name: response.data.nickname, // 로그인한 유저의 닉네임 넣기
          buyer_tel: response.data.phone, // 로그인한 유저의 전화번호 넣기
          buyer_addr: response.data.address + ' ' + response.data.address_detail, // 로그인한 유저 주소 넣기
        },
        (result_success) => {
          this.verifyIam(result_success);
          //성공할 때 실행 될 콜백 함수
          var msg = '결제가 완료되었습니다.';
          msg += '고유ID : ' + result_success.imp_uid; // imp_uid order테이블에 추가예정
          msg += '상점 거래ID : ' + result_success.merchant_uid;
          msg += '결제 금액 : ' + result_success.paid_amount;
          msg += '카드 승인번호 : ' + result_success.apply_num;
        },
        (result_failure) => {
          //실패시 실행 될 콜백 함수
          var msg = '결제에 실패하였습니다.';
          msg += '에러내용 : ' + result_failure.error_msg;
          this.$swal({
            position: 'top-end',
            icon: 'error',
            title: '결제실패!!',
            showConfirmButton: false,
            timer: 1500,
          });
        }
      );
    },
    async verifyIam(result_success) {
      // console.log('result_success', result_success);
      const { data } = await verifyIamport(result_success.imp_uid);
      // console.log(data.response.status);
      if (data.response.status == 'paid') {
        const data1 = {
          uuid: this.uuid,
          event_id: this.event.event_id,
          imp_uid: result_success.imp_uid,
          ship_status: 1,
          pay_status: 1,
        };
        // console.log('data1', data1);
        const responsedata = await completePayment(data1);
        // console.log('1111111', responsedata);
        if (responsedata.data == 'SUCCESS') {
          this.$swal({
            position: 'top-end',
            icon: 'success',
            title: '결제완료!!',
            showConfirmButton: false,
            timer: 1500,
          });
          this.paycheck = false;
        } else {
          this.$swal({
            position: 'top-end',
            icon: 'error',
            title: '결제실패!!',
            showConfirmButton: false,
            timer: 1500,
          });
          this.paycheck = true;
        }
      }
    },
    async cancleiamport() {
      const { data } = await fetchOrder(this.event.event_id);
      // console.log('결제테이블', data);

      for (let i = 0; i < data.length; i++) {
        if (this.$store.state.uuid == data[i].uuid) {
          const response = await cancleOrder(data[i].imp_uid);
          // console.log(response);
          if (response.data.code == 0) {
            const data = await deleteOrdertable(this.event.event_id);
            // console.log(data);
            this.$swal({
              position: 'center',
              icon: 'success',
              title: '결제취소완료!!',
              showConfirmButton: false,
              timer: 1500,
            });
            this.paycheck = true;
          } else if (response.data.code == 1) {
            this.$swal({
              position: 'center',
              icon: 'error',
              title: '결제취소실패!! 이미 전액취소 되었습니다',
              showConfirmButton: false,
              timer: 1500,
            });
            this.paycheck = false;
          } else {
            this.$swal({
              position: 'center',
              icon: 'error',
              title: '결제취소실패!! 올바른 상품인지 확인하세요',
              showConfirmButton: false,
              timer: 1500,
            });
            this.paycheck = false;
          }
        }
      }
    },
  },
};
</script>

<style scoped>
@font-face {
  font-family: 'NEXON Lv1 Gothic OTF';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
@import url('https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;600;700;800&display=swap');

* {
  box-sizing: border-box;
  padding: 0;

  margin: 0;
  font-family: 'Open Sans', sans-serif;
}
/* body {
  line-height: 1.5;
} */
#payment {
  margin-left: 1rem;
  margin-right: 1rem;
}
.card {
  margin-top: 25%;
}
.card-wrapper {
  max-width: 1100px;
  margin: 0 auto;
}
img {
  width: 100%;
  display: block;
}
.img-display {
  overflow: hidden;
}
.img-showcase {
  display: flex;
  width: 100%;
  transition: all 0.5s ease;
}
.img-showcase img {
  min-width: 100%;
}
.img-select {
  display: flex;
}
.img-item {
  margin: 0.3rem;
}
.img-item:nth-child(1),
.img-item:nth-child(2),
.img-item:nth-child(3) {
  margin-right: 0;
}
.img-item:hover {
  opacity: 0.8;
}
.product-content {
  padding: 2rem 1rem;
}
.product-title {
  font-size: 3rem;
  text-transform: capitalize;
  font-weight: 700;
  position: relative;
  color: #12263a;
  margin: 1rem 0;
}
.product-title::after {
  content: '';
  position: absolute;
  left: 0;
  bottom: 0;
  height: 4px;
  width: 80px;
  background: #12263a;
}
.product-tag {
  text-decoration: none;
  text-transform: uppercase;
  font-weight: 400;
  font-size: 0.9rem;
  display: inline-block;
  margin-bottom: 0.5rem;
  background: #256eff;
  color: #fff;
  padding: 0.2rem 1rem;
  transition: all 0.5s ease;
}
.product-tag:hover {
  opacity: 0.9;
}

.product-price {
  margin: 1rem 0;
  font-size: 2rem;
  font-weight: 700;
}
.product-price span {
  font-weight: 400;
}
.price span {
  color: #256eff;
  font-weight: 800;
}
.product-detail h2 {
  text-transform: capitalize;
  color: #12263a;
  padding-bottom: 0.6rem;
}
.product-detail p {
  font-size: 0.9rem;
  padding: 0.3rem;
  opacity: 0.8;
}
.product-detail ul {
  margin: 1rem 0;
  font-size: 1.2rem;
}
.product-detail ul li {
  list-style: none;
  background: url(../../assets/img/check.jpg) left center no-repeat;
  background-size: 18px;
  padding-left: 1.7rem;
  margin: 0.4rem 0;
  font-weight: 600;
  opacity: 0.9;
}
.product-detail ul li span {
  font-weight: 400;
}
.join-info {
  margin: 1.5rem 0;
}
.join-info input,
.join-info .btn {
  border: 1.5px solid #ddd;
  border-radius: 25px;
  text-align: center;
  padding: 0.45rem 0.8rem;
  outline: 0;
  margin-right: 0.2rem;
  margin-bottom: 1rem;
}

.join-info .btn {
  width: 40%;
  cursor: pointer;
  color: #fff;
  letter-spacing: 2px;
  padding-top: 5px;
  font-weight: 700;
  font-size: 1.1rem;
  background: #f3118e;
}

.join-info .btn:disabled {
  cursor: pointer;
  color: #fff;
  background: #000;
}
.purchase-info .btn:hover {
  opacity: 0.9;
}
.detail-image {
  margin-right: 40vh;
  /* right: 700px; */
}
.tab-container {
  height: 20vh;
  display: flex;
  justify-content: center;
  align-items: center;
  user-select: none;
  margin-top: 5rem;
}
.tabs {
  display: flex;
  position: relative;
  text-align: center;
  width: 80%;
  height: 4rem;
  background-color: #fff;
  overflow: hidden;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
}
.tabs .tab {
  width: 50%;
  height: 100%;
  margin-top: 0.5rem;
  text-align: center;
  color: #fff;
  cursor: pointer;
  z-index: 2;
}
.tabs .border {
  position: absolute;
  width: 100%;
  height: 1rem;
  background: #256eff;
  bottom: 0;
  z-index: 2;
  transition: 0.4s;
}
.tabs .background {
  z-index: 1;
  position: absolute;
  width: 100%;
  height: 100%;
  background: #f3118e;
  bottom: 0;
  transition: 0.4s;
}
#tab1-title {
  color: #fff;
  font-size: 1.2rem;
  padding-left: 90%;
  letter-spacing: 1rem;
}
#tab2-title {
  color: #000;
  font-size: 1.2rem;
}

.detail-contain {
  display: flex;
  justify-content: center;
  align-content: center;
}
.btns {
  display: flex;
  flex-direction: row-reverse;
  margin-right: 10%;
}
@media screen and (min-width: 992px) {
  .card {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    grid-gap: 1.5rem;
  }
  .card-wrapper {
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .product-imgs {
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  .product-content {
    padding-top: 0;
  }
}
</style>
