var circulo = {};

circulo.merge_arrays = function(array_1, array_2){
	var array_3 = [];
	for(var i in array_1){
   		var shared = false;
   		for (var j in array_2){
       		if (array_2[j].id === array_1[i].id) {
           		shared = true;
           		break;
       		}
       	}
   		if(!shared){ 
   			array_3.push(array_1[i])
   		}
	}
	array_3 = array_3.concat(array_2);
	return array_3;
};