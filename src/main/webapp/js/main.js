$('#myModal').modal('hide');
$('#myModal').on('hidden', () => {
    $('#myModalNew').modal('show')
});