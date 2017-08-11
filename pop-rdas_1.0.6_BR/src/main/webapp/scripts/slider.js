;(function($){

	/*左侧蓝色导航部分高度控制*/

	var contentHeight = $(window).height()-250;
	if($('.content_l').height()<contentHeight){
		
		$('.content_l').height(contentHeight);
		
	}

	/*选项卡函数*/

	$('.tab_head li').on('click',function () {
		$(this).addClass('active').siblings().removeClass('active');
		$('.tab1').eq($(this).index()).addClass('active').siblings().removeClass('active');
	});
	$('.tab_head2 input').on('click',function () {
		$('.tab2').eq($(this).parent().index()).addClass('active').siblings().removeClass('active');
	});
	$('.tab_head3 input').on('click',function () {
		$('.tab3').eq($(this).parent().index()).addClass('active').siblings().removeClass('active');
	});


	/*下拉框*/
	
	$('.alignL input').click(function () {   
	  $(".triCon2").hide();
	  $(this).next().show();
	 });
	 $('.triCon2 li').click(function () {    
	  var tri = $(this).find('a').html();
	  $(this).parent().parent().find('.align1').val(tri);
	  $('.triCon2').hide();
	 });
	 $(document).click(function(){    
	  $(".triCon2").hide();
	 });
	 $('.alignL').click(function (event) {    
	  event.stopPropagation();
	 });


})(jQuery);