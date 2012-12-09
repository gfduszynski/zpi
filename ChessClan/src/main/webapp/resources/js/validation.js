function hideTips(input){
    $(input).tipsy('hide');
}
function hideAll(){
    $('.tipsy').remove();
}
function showTips(input, criteria, index, notErrorMsg, offst, pos){
    $(document).ready(function(){
        var jInput = $(input);
        if(jInput.attr('alt') != undefined){
            var msg = jInput.attr('alt').split(';')[index || 0];
        }else{
            var msg = $('#hiddenMsg').val();
        }
        if(notErrorMsg){
            msg = '<span style="color:green;">'+msg+'</span>';
        }
        jInput.tipsy({
            fallback:msg, 
            trigger:'manual', 
            gravity:(pos || 'w'), 
            html:true, 
            offset: (offst || 0), 
            opacity : 0.9
        });
        if(!criteria(jInput.val())){
            jInput.tipsy('show');
            console.log('Show!');
        }
        jInput.keyup(function() {
            if(criteria(jInput.val())){
                jInput.tipsy('hide');
            }else{
                jInput.tipsy('show');
            }
        });
    });
}