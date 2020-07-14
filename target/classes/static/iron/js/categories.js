$("#categories-img-editbutton").click(function() {
	$("#categories-img-edit").trigger("click")

})
$("#categories-img-edit").change(function() {
	var file = $("#categories-img-edit")[0].files[0]
	let fileReader = new FileReader();
	fileReader.addEventListener("load", function() {
		$("#categories-img").attr("src", fileReader.result)
	}, false);
	fileReader.readAsDataURL(file)
})
$("#categories-edit").click(function() {
	var name = $("#categories-name").val();
	var detail = $("#categories-detail").val();
	var id = window.categories.id
	var page = [
		[$ {
			url
		}]
	] + "/back/categories/edit"
	var file = $("#categories-img-edit")[0].files[0]

	var formData = new FormData();
	formData.append("file", file);
	formData.append("name", name);
	formData.append("detail", detail);
	formData.append("id", id);
	$.ajax({
		url: page,
		type: 'POST',
		cache: false,
		data: formData,
		processData: false,
		contentType: false,
		success: function(res) {
			$("#categories-img").attr("src", [
				[$ {
					qiniu
				}]
			] + res)
			alert("修改成功")
		}
	})
})
$(".CategoriesEdit").click(function() {
	var page = [
		[$ {
			url
		}]
	] + "/back/categories/look"
	var id = $(this).attr("data-id")
	$.post(page, {
			"id": id
		}, function(res) {
			window.categories = res
			$("#categories-name").val(res.name)
			$("#categories-detail").val(res.detail)
			$("#categories-img").attr("src", [
				[$ {
					qiniu
				}]
			] + res.img)
		}

	)

})
$("#categoriesdelete").click(function() {


})