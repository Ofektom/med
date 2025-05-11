"user strict";

document.addEventListener('DOMContentLoaded', () => {
  const sidebarToggleButtons = document.querySelectorAll('.sidebarToggle');

  // Handle sidebar toggle
  sidebarToggleButtons.forEach(button => {
      button.addEventListener('click', event => {
          event.preventDefault();
          document.body.classList.toggle('sb-sidenav-toggled');
          localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
      });
  });

  // Sidebar close & toggle behavior using event delegation
  document.addEventListener('click', (event) => {
      if (event.target.closest('.closesidebar')) {
          document.querySelector('.sidebar').classList.toggle('close');
          document.querySelector('.sidebar-backdrop').classList.remove('active');
      }
      if (event.target.closest('.sidebarToggle')) {
          document.querySelector('.sidebar').classList.remove('close');
          document.querySelector('.sidebar-backdrop').classList.add('active');
      }
  });

  // Function to check screen size and toggle class
  function checkScreenSize() {
      if (window.innerWidth <= 991) {
          document.body.classList.add("sb-sidenav-toggled");
      } else {
          document.body.classList.remove("sb-sidenav-toggled");
      }
  }

  // Listen for resize events efficiently
  let resizeTimeout;
  window.addEventListener("resize", () => {
      clearTimeout(resizeTimeout);
      resizeTimeout = setTimeout(checkScreenSize, 150);
  });

  // Initial check on page load
  checkScreenSize();
});





// fullscreen js
if (($("#fullscreen").length) > 0){
let myDocument = document.documentElement;
let btn = document.getElementById("fullscreen");
btn.textContent = "Go Fullscreen";
btn.addEventListener("click", () => {
    if (btn.textContent == "Go Fullscreen") {
        if (myDocument.requestFullscreen) {
            myDocument.requestFullscreen();
        }
        else if (myDocument.msRequestFullscreen) {
            myDocument.msRequestFullscreen();
        }
        else if (myDocument.mozRequestFullScreen) {
            myDocument.mozRequestFullScreen();
        }
        else if (myDocument.webkitRequestFullscreen) {
            myDocument.webkitRequestFullscreen();
        }
        btn.textContent = "Exit Fullscreen";
    }
    else {
        if (document.exitFullscreen) {
            document.exitFullscreen();
        }
        else if (document.msexitFullscreen) {
            document.msexitFullscreen();
        }
        else if (document.mozexitFullscreen) {
            document.mozexitFullscreen();
        }
        else if (document.webkitexitFullscreen) {
            document.webkitexitFullscreen();
        }
        btn.textContent = "Go Fullscreen";
    }
});
}

// apex charts--------------------------------------

  // apexchart totaldeath
  var options = {
    series: [{
    name: 'Last Month',
    data: [20, 15, 10, 30, 42, 75, 80],
    color:'#b91c1c'
  },{
    name: 'This Month',
    data: [11, 32, 40, 50, 70, 70, 50],
    color:'#dc2626'
  }],
    chart: {
    height: 350,
    type: 'area',
    toolbar:false,
    fontFamily: 'plus jakarta'
  },
  grid:{
    yaxis: {
      lines: {
          show: false
      }
  }
  },
  dataLabels: {
    enabled: false
  },
  stroke: {
    curve: 'smooth'
  },
  xaxis: {
    type: 'day',
  categories: ["Mon","Tue","Wed","Thu","Fri","Sat","Sun"]
  },
  legend: {
    position: 'bottom',
    horizontalAlign:'center',
    fontSize:'14px',
    fontWeight:'bold',
    offsetX: 0,
    offsetY: 0,
    itemMargin: {
      horizontal:5,
      vertical:0
    }
  }
  };
  if (($("#totaldeath").length) > 0){
    var chart = new ApexCharts(document.querySelector("#totaldeath"), options);
    chart.render();
  }

  // apexchart totalaccident
  var options = {
  series: [{
  name: 'Last Month',
  data: [4, 30, 15, 20, 45, 40, 60],
  color:'#fbbf24' 
  }, {
  name: 'This Month',
  data: [5, 20, 25, 30, 10, 30, 50],
  color:'#eab308'
  }],
  chart: {
  height: 350,
  type: 'area',
  toolbar:false,
  fontFamily: 'plus jakarta'
  },
  dataLabels: {
  enabled: false
  },
  stroke: {
  curve: 'smooth'
  },
  grid:{
  yaxis: {
    lines: {
        show: false
    }
  }
  },
  xaxis: {
  type: 'day',
  categories: ["Mon","Tue","Wed","Thu","Fri","Sat","Sun"]
  },
  legend: {
  fontSize:'14px',
  fontWeight:'bold',
  itemMargin: {
    horizontal:5,
    vertical:0
  }
  }
  };
  if (($("#totalaccident").length) > 0){
  var chart = new ApexCharts(document.querySelector("#totalaccident"), options);
  chart.render();
  }

  // patient by department
  var options = {
    series: [
  {
    name: 'Neurology',
    data: [44, 55, 41, 67, 22, 43, 21, 10, 20, 5, 20, 10],
    color: '#4B49AC'
  }, 
  {
    name: 'Dental Care ',
    data: [13, 23, 20, 8, 13, 27, 33, 12, 40, 20, 10, 25],
    color: '#839CFA'
  }, 
  {
    name: 'Gynocology',
    data: [11, 17, 15, 15, 21, 14, 15, 13, 40, 35, 15, 25],
    color: '#6C6CD9'
  },
  {
    name: 'Orthopedic ',
    data: [44, 55, 41, 67, 22, 43, 21, 49, 40, 15, 25, 25],
    color: '#40349E'
  }
  ],
  chart: {
    id: 'patbydepartment',
    height: 350,
    type: 'bar',
    stacked: true,
    toolbar: {
      show: false
    },
    zoom: {
      enabled: false,
    }
  },
  plotOptions: {
    bar: {
      columnWidth: '33px',
      borderRadius: 2
    },
  },
  dataLabels:{
  enabled:false,
  },
  responsive: [{
    breakpoint: 650,
    options: {
      legend: {
        position: 'bottom',
        offsetX: -10,
        offsetY: 0,
        itemMargin: {
          horizontal: 5,
          vertical: 5
        },
      },
      plotOptions: {
        bar: {
          columnWidth: '15px',
        },
      }
    }
  }],
  xaxis: {
    categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
      'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
    ]
  },
  fill: {
    opacity: 1,
    // colors: ['#01B8FF', '#61D3FF', '#8ADEFF', '#BEEDFF']
  },
  legend: {
    position: 'top',
    offsetX: 'right',
    fontWeight:'500',
    fontSize:13,
    itemMargin: {
      horizontal:20,
      vertical:0
    },
    markers: {
      width: 25,
      height:10,
      radius:2
    },
    labels: {
      colors: "#676565",
    }
    
  }
  };
  
  if (($("#patbydepartment").length) > 0){
    var chart = new ApexCharts(document.querySelector("#patbydepartment"), options);
    chart.render();
  }

  // overall progress chart
  var options = {
    series: [75,65,85,60],
    chart: {
    height: 350,
    type: 'radialBar',
    fontFamily: 'plus jakarta',
  },
  plotOptions: {
    radialBar: {
      dataLabels: {
        name: {
          fontSize: '20px',
        },
        value: {
          fontSize: '24px',
          fontWeight:700,
          color: '#0E0E23'
        },
        total: {
          show: true,
          label: 'Total',
          color: '#0E0E23',
          fontSize: '14px',
          fontWeight:700,
        }
      }
    }
  },
  fill: {
    opacity: 1
  },
  labels: ['Neurology', 'Skin Care', 'Dental', 'Orthopedic']
  };
  if (($("#overallprogress").length) > 0){
  var chart = new ApexCharts(document.querySelector("#overallprogress"), options);
  chart.render();
  }

  // earning chart
  var options = {
    series: [85, 65.5, 70, 75, 80],
    chart: {
    height:320,
    type: 'polarArea'
  },
  labels: ['Neurology', 'Dental Care', 'Gynocology', 'Orthopedic', 'Skin Care'],
  fill: {
    opacity: 1
  },
  stroke: {
    width: 1,
    colors: undefined
  },
  yaxis: {
    show: false
  },
  plotOptions: {
    polarArea: {
      rings: {
        strokeWidth: 0
      },
      spokes: {
        strokeWidth: 0
      },
    }
  },
  theme: {
    monochrome: {
      enabled: false,
      shadeTo: 'light',
      shadeIntensity: 0.6,
      color:'#008FFB'
    }
  },
  legend: {
    position: 'bottom',
    horizontalAlign:'start',
    fontSize:'13px',
    fontWeight:500,
    labels: {
      colors: "#676565",
    },
    itemMargin: {
      horizontal:5,
      vertical:5
    },
    markers: {
      width: 20,
      height:10,
      radius:2
    },
  }
  };
  if (($("#earningchart").length) > 0){
  var chart = new ApexCharts(document.querySelector("#earningchart"), options);
  chart.render();
  }


// ----------------------------------------------------------------------------------------------------------------------

// grid and list view
  $("#list").click(function(){
      $(".list-show").removeClass("d-none");
      $(".list-hide").addClass("d-none");
  })
  $("#grid").click(function(){
    $(".list-show").addClass("d-none");
    $(".list-hide").removeClass("d-none");
  })

// doctor list datatable 
  $("#list").click(function(){
    if (($("#doctorlist").length) > 0){
      new DataTable('#doctorlist', {
        retrieve: true,
        responsive: true,
        "order": [5, 10, 15],
        lengthMenu: [5, 10, 15],
        "columnDefs": [ {
          "targets"  : 'no-sort',
          "orderable": false,
        }]
      });
    }
    if (($("#doctorlist_filter").length) > 0){
      $(document).ready(function(){
        $("#doctorlist_filter input").attr("placeholder","Doctor Name");
      });
    }
  });

// appointmentlist datatable
if (($("#appointmentlist").length) > 0){
new DataTable('#appointmentlist', {
responsive: true,
"order": [],
lengthMenu: [5, 10, 15],
  "columnDefs": [ {
    "targets"  : 'no-sort',
    "orderable": false, 
  }]
});
}
// operation datatable
if (($("#operationtd").length) > 0){
  new DataTable('#operationtd', {
  responsive: true,
  "order": [],
  lengthMenu: [4, 8, 12],
    "columnDefs": [ {
      "targets"  : 'no-sort',
      "orderable": false,
    }]
  });
  }
  if (($("#reportstd").length) > 0){
    new DataTable('#reportstd', {
    responsive: true,
    "order": [],
    lengthMenu: [10, 15],
      "columnDefs": [ {
        "targets"  : 0,
        "orderable": false,
      }]
    });
    }
    if (($("#assetslisttd").length) > 0){
      new DataTable('#assetslisttd', {
      responsive: true,
      "order": [],
      lengthMenu: [10, 15],
        "columnDefs": [ {
          "targets"  : 0,
          "orderable": false,
        }]
      });
    }  
    
    $('#productinventory').on('shown.bs.modal', function () { 
      function initializeDataTable(tableId, placeholderText) {
          if (!$.fn.DataTable.isDataTable(tableId)) {
              let table = $(tableId).DataTable({
                  responsive: true,
                  order: [],
                  lengthMenu: [5, 10, 15],
                  columnDefs: [{
                      targets: 'no-sort',
                      orderable: false,
                  }]
              });
  
              // Set search input placeholder
              $(`${tableId}_filter input`).attr("placeholder", placeholderText);
          } else {
              // Adjust column widths for responsiveness when reopening modal
              $(tableId).DataTable().columns.adjust().responsive.recalc();
          }
      }
  
      // Show preloader and ensure tables are hidden
      $(".modal-preloader").show();
      $(".datatable-container").addClass('d-none');
  
      // Small delay to allow proper rendering
      setTimeout(() => {
          initializeDataTable('#distributedtd', 'Search Inventory');
          initializeDataTable('#addedtd', 'Search Inventory');
  
          // Hide preloader & show tables after rendering
          $(".modal-preloader").fadeOut(300, function () {
              $(".datatable-container").removeClass('d-none');
  
              // Force DataTable to adjust after becoming visible
              $('#distributedtd, #addedtd').DataTable().columns.adjust().responsive.recalc();
          });
  
      }, 500); // Adjust if needed
  });
  
  // When the modal is closed, reset tables again
  $('#productinventory').on('hidden.bs.modal', function () {
      $(".datatable-container").addClass('d-none'); // Hide tables again on close
      $(".modal-preloader").show();
  });

  
// allotroom datatable
if (($("#allotroomdt").length) > 0){
new DataTable('#allotroomdt', {
  responsive: true,
  "order": [],
  lengthMenu: [5, 10, 15],
    "columnDefs": [ {
      "targets"  : 'no-sort',
      "orderable": false
    }]
  });
}
// allotroom datatable
if (($("#paymntdt").length) > 0){
  new DataTable('#paymntdt', {
    responsive: true,
    "order": [],
      "columnDefs": [ {
        "targets"  : 'no-sort',
        "orderable": false
      }]
    });
}

// add department datatable
if (($("#adddepartmentdt").length) > 0){
  // adddepartment datatables
new DataTable('#adddepartmentdt', {
  responsive: true,
  "order": [],
  lengthMenu: [5, 10, 15],
    "columnDefs": [ {
      "targets"  : 'no-sort',
      "orderable": false,
    }]
  });
}
// patient list datatable
if (($("#patientlistdt").length) > 0){
    // patientlist datatables
new DataTable('#patientlistdt', {
  responsive: true,
  "order": [],
  lengthMenu: [5, 10, 15],
    "columnDefs": [ {
      "targets"  : 'no-sort',
      "orderable": false,
    }]
  });
}
// staff list datatable
if (($("#stafflisttd").length) > 0){
  // stafflist datatables
  new DataTable('#stafflisttd', {
    responsive: true,
    "order": [],
    lengthMenu: [5, 10, 15],
      "columnDefs": [ {
        "targets"  : 'no-sort',
        "orderable": false,
      }]
    });
  }

// Datepicker ------------------------------
var options={
  orientation: 'Bottom' 
}
var options2={
  orientation: 'Top' 
}
  if (($("#discharge_date_main").length) > 0){
  $(function(){
    $('#discharge_date_main').datepicker(options2);
  });
  }
  if (($("#allot_date_main").length) > 0){
    $(function(){
      $('#allot_date_main').datepicker(options2);
    });
  }
  if (($("#edtroom_dischdate").length) > 0){
    $(function(){
      $('#edtroom_dischdate').datepicker(options2);
    });
  }
  if (($("#edtroom_adddate2").length) > 0){
    $(function(){
      $('#edtroom_adddate2').datepicker(options);
    });
  }
  if (($("#edtroom_adddate").length) > 0){
    $(function(){
      $('#edtroom_adddate').datepicker(options);
    });
  }
  if (($("#edtroom_dischdate2").length) > 0){
    $(function(){
      $('#edtroom_dischdate2').datepicker(options);
    });
  }
  if (($("#edtroom_allotdate").length) > 0){
      $(function(){
        $('#edtroom_allotdate').datepicker(options2);
      });
  }
  if (($("#depart_date").length) > 0){
    $(function(){
      $('#depart_date').datepicker(options);
    });
  }
  if (($("#edtdepart_date").length) > 0){
    $(function(){
      $('#edtdepart_date').datepicker(options);
    });
  }
  if (($("#bk_apnt_dtmain").length) > 0){
    $(function(){
      $('#bk_apnt_dtmain').datepicker(options);
    });
  }
  if (($("#dob_main").length) > 0){
    $(function(){
      $('#dob_main').datepicker(options);
    });
  }
  if (($("#dob_main2").length) > 0){
    $(function(){
      $('#dob_main2').datepicker(options);
    });
  }
  if (($("#ed_apnt_dtmain").length) > 0){
    $(function(){
      $('#ed_apnt_dtmain').datepicker(options);
    });
  }


// Form validation---------------------------------------------------------------------------------------------------------------

  // form validation allot new room
  if (($("#allotnewroom").length) > 0){
    $(document).ready(function(){
      $('#allotnewroom').validate({
        rules:{
          room_no:"required",
          room_type:"required",
          allotedpatient:"required",
          allot_date:"required",
          discharge_date:"required"
        },
        messages:{
          room_no_message:"room number required",
          room_type:"room type required",
          allotedpatient:"patient name required",
          allot_date:"field cannot be empty",
          discharge_date:"field cannot be empty"
        }
      });
    });
  }

  // form validation allot edit room
  if (($("#edit_roomallot").length) > 0){
    $(document).ready(function(){
      $('#edit_roomallot').validate({
        rules:{
          edtroom_no:"required",
          edtroom_type:"required",
          edtroom_patient:"required",
          allot_date:"required",
          edt_dischdate:"required"
        }
      });
    });
  }

  // add department validation 
  if (($("#adddepartmentfm").length) > 0){
  $(document).ready(function(){
    $('#adddepartmentfm').validate({
      rules:{
        departmentname:"required",
        departmenthead:"required",
        departmentdate:"required",
        status:"required"
      },
      messages:{
        departmentname:"This field is required",
        departmenthead:"This field is required",
        departmentdate:"This field is required"
      }
    });
  });
  }

  // add department validation 
  if (($("#edt_depart_detail").length) > 0){
  $(document).ready(function(){
    $('#edt_depart_detail').validate({
      rules:{
      edt_depart_name:"required",
      edt_depart_head:"required",
      edt_depart_status:"required"
      },
      messages:{
      edt_depart_name:"This field is required",
      edt_depart_head:"This field is required",
      edt_depart_date:"This field is required"
      }
    });
  });
  }
  // add doctor
  if (($("#add_doctor").length) > 0){
    $(document).ready(function(){
      $('#add_doctor').validate({
        rules:{
          fname:"required",
          lname:"required",
          username:"required",
          gender:"required",
          mobile:"required",
          email:"required",
          pass:"required",
          repeatpass:"required",
          designation:"required",
          education:"required",
          department:"required",
          address:"required",
          city:"required",
          state:"required",
          country:"required",
          pincode:"required",
          dob:"required",
          status:"required",
        }
      });
    });
  }
  // edit doctor
  if (($("#edit_doctor").length) > 0){
    $(document).ready(function(){
      $('#edit_doctor').validate({
        rules:{
          fname:"required",
          lname:"required",
          username:"required",
          gender:"required",
          mobile:"required",
          email:"required",
          pass:"required",
          repeatpass:"required",
          designation:"required",
          education:"required",
          department:"required",
          address:"required",
          city:"required",
          state:"required",
          country:"required",
          pincode:"required",
          dob:"required",
          status:"required",
        }
      });
    });
  }
  // doctor profile 
  if (($("#edit_doctor_profile").length) > 0){
    $(document).ready(function(){
      $('#edit_doctor_profile').validate({
        rules:{
          username:"required",
          mobile:"required",
          email:"required",
          pass:"required",
          newpass:"required",
          repeatpass:"required",
          address:"required",
        }
      });
    });
  }
  // add patient
  if (($("#add_patient").length) > 0){
    $(document).ready(function(){
      $('#add_patient').validate({
        rules:{
          fname:"required",
          lname:"required",
          username:"required",
          gender:"required",
          mobile:"required",
          email:"required",
          pass:"required",
          repeatpass:"required",
          designation:"required",
          education:"required",
          department:"required",
          address:"required",
          city:"required",
          state:"required",
          country:"required",
          pincode:"required",
          dob:"required",
          status:"required",
          maritalstatus:"required",
          blood:"required",
          bloodpress:"required",
          sugar:"required",
          injury:"required",
          age:"required"
        }
      });
    });
  }
  
  // add patient
  if (($("#edit_patient").length) > 0){
    $(document).ready(function(){
      $('#edit_patient').validate({
        rules:{
          fname:"required",
          lname:"required",
          username:"required",
          gender:"required",
          mobile:"required",
          email:"required",
          pass:"required",
          repeatpass:"required",
          designation:"required",
          education:"required",
          department:"required",
          address:"required",
          city:"required",
          state:"required",
          country:"required",
          pincode:"required",
          dob:"required",
          status:"required",
          maritalstatus:"required",
          blood:"required",
          bloodpress:"required",
          sugar:"required",
          injury:"required",
          age:"required"
        }
      });
    });
  }

  // add appointment validation 
  if (($("#book_appointment").length) > 0){
    $(document).ready(function(){
      $('#book_appointment').validate({
        rules:{
          fname:"required",
          lname:"required",
          gender:"required",
          mobile:"required",
          email:"required",
          address:"required",
          bk_apnt_date:"required",
          from:"required",
          to:"required",
          consultingdoctor:"required",
          treatment:"required",
        }
      });
    });
  }
  if (($("#addasset_form").length) > 0){
    $(document).ready(function(){
      $('#addasset_form').validate({
        rules:{
         
        },
        message:{

        }
      });
    });
  }

  // edt appointment validation 
  if (($("#edt_appointment").length) > 0){
    $(document).ready(function(){
      $('#edt_appointment').validate({
        rules:{
          fname:"required",
          lname:"required",
          gender:"required",
          mobile:"required",
          email:"required",
          address:"required",
          bk_apnt_date:"required",
          from:"required",
          to:"required",
          consultingdoctor:"required",
          treatment:"required",
        }
      });
    });
  }


  // add staff validation
  if (($(".addstaffvalidate").length) > 0){
    $(document).ready(function(){
      $('.addstaffvalidate').validate({
        rules:{
          fname:"required",
          lname:"required",
          gender:"required",
          mobile:"required",
          email:"required",
          address:"required",
          dob:"required",
          age:"required",
          designation:"required",
          maritalstatus:"required",
          education:"required",
        }
      });
    });
  } 

  // edit staff validation
  if (($(".edtstaffvalidate").length) > 0){
    $(document).ready(function(){
      $('.edtstaffvalidate').validate({
        rules:{
          e_fname:"required",
          e_lname:"required",
          e_gender:"required",
          e_mobile:"required",
          e_email:"required",
          e_address:"required",
          e_dob:"required",
          e_age:"required",
          e_designation:"required",
          e_maritalstatus:"required",
          e_education:"required",
        }
      });
    });
  } 

  // add bill validation
  if (($(".addbillvalidate").length) > 0){
  $(document).ready(function(){
    $('.addbillvalidate').validate({
      rules:{
        billnumber:"required",
        adm_id:"required",
        patient_name:"required",
        doctor_name:"required",
        addadmit_date:"required",
        adddischarge_date:"required",
        discount:"required",
        total_amount:"required",
        payment_method:"required",
        payment_status:"required"
      }
    });
  });
  } 

  // edt bill validation
  if (($(".edtbillvalidate").length) > 0){
  $(document).ready(function(){
    $('.edtbillvalidate').validate({
      rules:{
        e_billnumber:"required",
        e_adm_id:"required",
        e_patient_name:"required",
        e_doctor_name:"required",
        e_edtadmit_date:"required",
        e_edtdischarge_date:"required",
        e_discount:"required",
        e_total_amount:"required",
        e_payment_method:"required",
        e_payment_status:"required"
      }
    });
  });
  } 

  // reset validation
  if (($(".resetvalidate").length) > 0){
  $(document).ready(function(){
    $('.resetvalidate').validate({
      rules:{
        email:"required"
      }
    });
  });
  } 

  // registeration validation
  if (($(".registervalidate").length) > 0){
    $(document).ready(function(){
      $('.registervalidate').validate({
        rules:{
          fullname:"required",
          email:"required",
          password:"required",
          cnfpass:"required",
          checkterms:"required"
        }
      });
    });
  } 
  // login validation
  if (($(".loginvalidate").length) > 0){
  $(document).ready(function(){
    $('.loginvalidate').validate({
      rules:{
        email:"required",
        password:"required"
      }
    });
  });
  }


// sticky header 
$(document).ready(function(){
  var scroll = $(window).scrollTop();
  // console.log(scroll);

  if (scroll > 200) 
    {
      $('.header').removeClass('mt-0 mt-3 mx-2 mx-sm-4  rounded-10');
    } 
    else if (scroll == 0) 
    {
      $('.header').addClass('mt-0 mt-3 mx-2 mx-sm-4  rounded-10');
    }
  
  $(window).scroll(function () {
    var scroll = $(window).scrollTop();
    // console.log(scroll);
    if (scroll > 150) 
    {
      $('.header').removeClass('mt-0 mt-3 mx-2 mx-sm-4  rounded-10');
    } 
    else if (scroll == 0) 
    {
      $('.header').addClass('mt-0 mt-3 mx-2 mx-sm-4  rounded-10');
    }
  });

});

// preloader
if (($("#preloader").length) > 0){
function closepreloader(){
  document.getElementById("preloader").style.display = 'none';
}
window.addEventListener("load",function(){
  setTimeout(closepreloader, 500);
});
}




// slim scroller
if (($("#notification").length) > 0){
$(document).ready(function () {
  $('#notification').slimScroll();
})
}
if (($("#recent_activity").length) > 0){
$(document).ready(function () {
  $('#recent_activity').slimScroll();
})
}
if (($("#notification-side").length) > 0){
  $(document).ready(function () {
    $('#notification-side').slimScroll();
  })
}
if (($("#sidebar-data").length) > 0){
    $(document).ready(function () {
      $('#sidebar-data').slimScroll();
    })
}
if (($("#top_department").length) > 0){
  $(document).ready(function () {
    $('#top_department').slimScroll();
  })
}
if (($("#message_box").length) > 0){
  $(document).ready(function () {
    $('#message_box').slimScroll();
  })
}
if (($("#notification_box").length) > 0){
  $(document).ready(function () {
    $('#notification_box').slimScroll();
  })
}
if (($("#pat_notes").length) > 0){
  $(document).ready(function () {
    $('#pat_notes').slimScroll();
  })
}
if (($("#schedule-box").length) > 0){
  $(document).ready(function () {
    $('#schedule-box').slimScroll();
  })
}
if (($("#activity_box").length) > 0){
  $(document).ready(function () {
    $('#activity_box').slimScroll();
  })
}
if (($("#speciality_box").length) > 0){
  $(document).ready(function () {
    $('#speciality_box').slimScroll();
  })
}
if (($("#pat-scroll").length) > 0){
  $(document).ready(function () {
    $('#pat-scroll').slimScroll();
  })
}
if (($("#operation_main").length) > 0){
  $(document).ready(function () {
    $('#operation_main').slimScroll();
  })
}

// placehholder

//datatable placeholder
if (($("#appointmentlist_filter").length) > 0){
  $(document).ready(function(){
    $("#appointmentlist_filter input").attr("placeholder","Search Appointment");
  });
}
if (($("#operationtd_filter").length) > 0){
  $(document).ready(function(){
    $("#operationtd_filter input").attr("placeholder","Search Operation");
  });
}
if (($("#patientlistdt_filter").length) > 0){
  $(document).ready(function(){
    $("#patientlistdt_filter input").attr("placeholder","Search Patient");
  });
}
if (($("#adddepartmentdt_filter").length) > 0){
  $(document).ready(function(){
    $("#adddepartmentdt_filter input").attr("placeholder","Search Department");
  });
}
if (($("#stafflisttd_filter").length) > 0){
  $(document).ready(function(){
    $("#stafflisttd_filter input").attr("placeholder","Search Staff");
  });
}
if (($("#allotroomdt_filter").length) > 0){
$(document).ready(function(){
  $("#allotroomdt_filter input").attr("placeholder","Room No.");
});
}
if (($("#paymntdt_filter").length) > 0){
  $(document).ready(function(){
    $("#paymntdt_filter input").attr("placeholder","Id, Patient Name .");
  });
}  
if (($("#reportstd_filter").length) > 0){
  $(document).ready(function(){
    $("#reportstd_filter input").attr("placeholder","Search Report");
  });
}
if (($("#assetslisttd_filter").length) > 0){
  $(document).ready(function(){
    $("#assetslisttd_filter input").attr("placeholder","Search Asset");
  });
}
// password visible
$(() => {
  $('[type="password"]').togglepassword('btn');
});

// msg
const toastTrigger = document.getElementById('liveToastBtn')
const toastLiveExample = document.getElementById('liveToast')

if (toastTrigger) {
  const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
  toastTrigger.addEventListener('click', () => {
    toastBootstrap.show()
  })
}

 // Copy write current year
 $(document).ready(function () {
  // Get the current year
  const currentYear = new Date().getFullYear();
  // Set the year in the target element
  $("#currentYear").text(currentYear);
});

