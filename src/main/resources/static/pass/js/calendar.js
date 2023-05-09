$(document).ready(function() {
    calendarInit();
});

function calendarInit() {

    // 날짜 정보 가져오기
    var date = new Date(); // 현재 날짜(로컬 기준) 가져오기
    var utc = date.getTime() + (date.getTimezoneOffset() * 60 * 1000); // uct 표준시 도출
    var kstGap = 9 * 60 * 60 * 1000; // 한국 kst 기준시간 더하기
    var today = new Date(utc + kstGap); // 한국 시간으로 date 객체 만들기(오늘)

    var thisMonth = new Date(today.getFullYear(), today.getMonth(), today.getDate());
    // 달력에서 표기하는 날짜 객체


    var currentYear = thisMonth.getFullYear(); // 달력에서 표기하는 연
    var currentMonth = thisMonth.getMonth(); // 달력에서 표기하는 월
    var currentDate = thisMonth.getDate(); // 달력에서 표기하는 일

    var passSeq = document.querySelector("input[name=passSeq]").value;  // 컨트롤러에서 model 을 통해 넘겨준 passSeq 데이터
    console.log(passSeq)

    // kst 기준 현재시간
    // console.log(thisMonth);

    // 캘린더 렌더링
    renderCalendar(thisMonth);

    function renderCalendar(thisMonth) {

        // 렌더링을 위한 데이터 정리
        currentYear = thisMonth.getFullYear();
        currentMonth = thisMonth.getMonth();
        currentDate = thisMonth.getDate();

        // 이전 달의 마지막 날 날짜와 요일 구하기
        var startDay = new Date(currentYear, currentMonth, 0);
        var prevDate = startDay.getDate();
        var prevDay = startDay.getDay();

        // 이번 달의 마지막날 날짜와 요일 구하기
        var endDay = new Date(currentYear, currentMonth + 1, 0);
        var nextDate = endDay.getDate();
        var nextDay = endDay.getDay();

        // console.log(prevDate, prevDay, nextDate, nextDay);

        // 현재 월 표기
        document.querySelector('.month').textContent = currentYear + '.' + (currentMonth + 1)

        // 렌더링 html 요소 생성
        calendar = document.querySelector('.dates')
        calendar.innerHTML = '';

        // 지난달
        for (var i = prevDate - prevDay + 1; i <= prevDate; i++) {
            calendar.innerHTML = calendar.innerHTML + '<div class="day prev disable">' + i + '</div>'
        }
        // 이번달
        for (var i = 1; i <= nextDate; i++) {
            calendar.innerHTML = calendar.innerHTML + '<a href="javascript:void(0);" class="day current">' + i + '</a>';
        }
        // 다음달
        for (var i = 1; i <= (7 - nextDay == 7 ? 0 : 7 - nextDay); i++) {
            calendar.innerHTML = calendar.innerHTML + '<div class="day next disable">' + i + '</div>'
        }

        // 오늘 날짜 표기
        if (today.getMonth() == currentMonth) {
            todayDate = today.getDate();
            var currentMonthDate = document.querySelectorAll('.dates .current');
            currentMonthDate[todayDate -1].classList.add('today');
        }


        const dateElements = document.querySelectorAll('.dates .day.current');

                          dateElements.forEach((dateElement) => {
                            dateElement.addEventListener('click', function() {
                              const clickedDate = dateElement.textContent;
                              const clickedMonth = currentMonth + 1;

                              $.ajax({
                                        url: "http://localhost:8080/passes/reserve_date",
                                        type: 'POST',
                                        contentType: 'application/json',
                                        data: JSON.stringify({ year: currentYear, month: clickedMonth, date: clickedDate })
                                      })
                              .done(function(json) {
                                   console.log(json);
                                   renderReservationList(json);
                               })
                            });
                          });

    }


      function renderReservationList(instructors) {
        const list = $('<ul>');

          // time순으로 instructors 배열을 정렬합니다.
          const sortedInstructors = instructors.sort((a, b) => {
            const aTime = parseInt(a.time.split('-')[0].replace(':', ''));
            const bTime = parseInt(b.time.split('-')[0].replace(':', ''));
            return aTime - bTime;
          });

          sortedInstructors.forEach((instructor) => {

            const listItem = $('<li>');
            const reserveNumber = instructor.reserveNumber || 0; // reserveNumber 값이 없으면 0으로 초기화

            const box = $('<div class="box">');
            const profileImg = $('<img src="/admin/img/undraw_profile_2.svg" style="width:100px">');
            const timeSpan = $(`<span style="margin-left: 10px;">${instructor.time}</span>`);
            const countSpan = $(`<span style="margin-left: 10px;">${reserveNumber}/${instructor.limitNumber}</span>`);
            const reserveBtn = $('<button>예약</button>');

                // 예약 버튼 클릭 시 instructor의 reserveNumber 값 증가
            reserveBtn.click(() => {
              if (reserveNumber !== undefined && reserveNumber !== null && reserveNumber < instructor.limitNumber) {
                instructor.reserveNumber = reserveNumber + 1;
                countSpan.text(`${instructor.reserveNumber}/${instructor.limitNumber}`);
                $.ajax({
                  url: 'http://localhost:8081/job/launcher',
                  method: 'POST',
                  data: JSON.stringify({name: 'usePassesJob', jobParameters: {userId: 'A1000001', passSeq: passSeq}}),
                  contentType: "application/json; charset=utf-8",
                  dataType: 'json',
                  success: function(response) {
                    alert('수업이 예약되었습니다!');
                    location.reload();
                    console.log(response);
                  },
                  error: function(xhr, status, error) {
                    console.error(error);
                  }
                });
              }
            });

                box.append(profileImg, timeSpan, countSpan, reserveBtn);
                listItem.html(`${instructor.name}`);
                listItem.append(box);

                list.append(listItem);
          });

          $('.reservation-list').html(list);
      } // instructors 리스트를 Time 순서대로 나열





    // 이전달로 이동
        document.querySelector('.go-prev').addEventListener('click', function() {
          thisMonth = new Date(currentYear, currentMonth - 1, 1);
          renderCalendar(thisMonth);
        });

        // 다음달로 이동
        document.querySelector('.go-next').addEventListener('click', function() {
          thisMonth = new Date(currentYear, currentMonth + 1, 1);
          renderCalendar(thisMonth);
        });

}










