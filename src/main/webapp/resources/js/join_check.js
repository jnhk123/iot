var join = {
common: {
	empty: { code: 'empty',		desc: '입력하세요' },
	space: { code: 'space',		desc: '공백없이 입력하세요' },
	min: { code: 'min', desc: '최소 5자이상 입력하세요'},
	max: { code: 'max', desc: '최소 10자이하 입력하세요'}
},

pwd_ck_status: function( pwd_ck ){
	if( pwd_ck == $('[name=userpwd]').val() ) return this.pwd.equal;
	else 		return this.pwd.notequal;		
},

pwd: {
	invalid: { code: 'invalid',  desc: '영문 대/소문자, 숫자만 입력'},
	lack: { code: 'lack', desc: '영문 대/소문자,숫자 모두 포함해야함'},
	valid: { code: 'valid', desc: '사용가능한 비밀번호'},
	equal: { code: 'valid', desc: '비밀번호가 일치함' },
	notequal: { code: 'invalid', desc: '비밀번호가 일치하지 않음' }	
},
pwd_status: function( pwd ){//영문 대소문자, 숫자 모두 포함
	var space = /\s/g;
	var reg = /[^a-zA-Z0-9]/g;
	var upper = /[A-Z]/g, lower = /[a-z]/g, digit = /[0-9]/g;
	if( pwd=='' )		return this.common.empty;
	else if( pwd.match(space) ) return this.common.space;
	else if( reg.test(pwd) ) return this.pwd.invalid;
	else if( pwd.length<5 ) return this.common.min;
	else if( pwd.length>10 ) return this.common.max;
	else if( !upper.test(pwd) | !lower.test(pwd) | !digit.test(pwd) ) 
		return this.pwd.lack;
	else	return this.pwd.valid;		
},

id: {	
	invalid: { code: 'invalid', desc: '영문소문자, 숫자만 입력'},
	valid: { code: 'valid', desc: '아이디 중복확인 하세요' },
	usable: { code: 'usable', desc: '사용 가능한 아이디'},
	unusable: { code: 'unusable', desc: '이미 사용중인 아이디'}
},		

id_usable: function( data ){
	if( data == 'true' ) return this.id.usable;
	else return this.id.unusable;		
},
id_status: function( id ){ //영문소문자, 숫자
	var reg = /[^a-z0-9]/g;
	var space = /\s/g;
	if( id=='' ){
		return this.common.empty;
	}else if( id.match(space) ){
		return this.common.space;
	}else if( reg.test(id) ){
		return this.id.invalid;
	}else if( id.length<5 ){ //5문자이상
		return this.common.min;
	}else if( id.length>10 ){ //10문자이하
		return this.common.max;
	}else{
		return this.id.valid;
	}
},

email_status: function( email ){
	var reg = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

	if( email=='' )	return this.common.empty;
	else if( reg.test(email) )	return this.email.valid;
	else return this.email.invalid;
},
email:{
	valid: { code:'valid', desc:'유효한 이메일' },
	invalid: { code:'invalid', desc:'유효하지 않은 이메일' }
}
		
}



















