function main()
{
	/*let parameters = {x:10};
    show_tween(parameters);*/

	let c = document.getElementById('acanvas').getContext('2d');

	funcoes(c);

	c.enter_t_s(0,0,4,4);
	c.desenho();
	c.leave();
}

function funcoes(gc)
{
	/*gc.update = update;*/
	gc.desenho = desenho;
	gc.enter = enter;
	gc.enter_t_s = enter_t_s;
	gc.leave = leave;
	gc.leave_fs_ss = leave_fs_ss;
	gc.leave_fs = leave_fs;
	gc.leave_ss = leave_ss;
	gc.ceu = ceu;
	gc.ceu_caminho = ceu_caminho;
	gc.cerca_ovelha = cerca_ovelha;
	gc.la = la;
	gc.cerca = cerca;
	gc.tabua_vertical = tabua_vertical;
	gc.tabua_horizontal = tabua_horizontal;
	gc.parafuso = parafuso;
	gc.chao = chao;
	gc.ovelha = ovelha;
	gc.cara_ovelha = cara_ovelha;
	gc.contorno_cara_ovelha = contorno_cara_ovelha;
	gc.boca_ovelha = boca_ovelha;
	gc.olhos_ovelha = olhos_ovelha;
	gc.esclerotida = esclerotida;
	gc.pupila = pupila;
	gc.corno = corno;
	gc.balao = balao;
	gc.texto_2 = texto_2;
	gc.texto_0_p1 = texto_0_p1;
	gc.texto_0_p2 = texto_0_p2;
	gc.pessoa = pessoa;
	gc.braco = braco;
	gc.contorno_braco = contorno_braco;
	gc.mao = mao;
	gc.perna = perna;
	gc.coxa = coxa;
	gc.canela = canela;
	gc.meia = meia;
	gc.sapato = sapato;
	gc.cabeca = cabeca;
	gc.contorno_cara_pessoa = contorno_cara_pessoa;
	gc.olho_pessoa = olho_pessoa;
	gc.esclerotida_pessoa = esclerotida_pessoa;
	gc.sobrancelha = sobrancelha;
	gc.gota = gota;
	gc.boca_pessoa = boca_pessoa;
	gc.orelha = orelha;
	gc.cabelo = cabelo;
	gc.tronco = tronco;
	gc.calcoes = calcoes;
	gc.manga = manga;
}

function desenho()
{
	this.ceu();

	this.enter();
		this.cerca_ovelha();
	this.leave();

	this.pessoa();

	this.enter();
		this.chao();
	this.leave_fs('green');
}

/*function show_tween(parameters)
{
    let tween = new TWEEN.Tween(parameters)
        .to({x:40}, 2000)
        .easing(TWEEN.Easing.Cubic.InOut)
        .yoyo(true)
        .repeat(Infinity);
    tween.start();
}

function update(parameters)
{
	this.clearRect(0,0, 128,128);
    this.cerca_ovelha(parameters);
    this.desenho();
    requestAnimationFrame(function(){this.update(parameters);});
    TWEEN.update();
}*/

function enter() {this.save();}

function enter_t_s(Tx,Ty, Sx,Sy)
{
	this.save();
	this.translate(Tx, Ty);
	this.scale(Sx, Sy);
}

function leave(){this.restore();}

function leave_fs_ss(fs, ss)
{
	this.fillStyle = fs;
	this.strokeStyle = ss;
	this.fill();
	this.stroke();
	this.restore();
}

function leave_fs(fs)
{
	this.fillStyle = fs;
	this.fill();
	this.restore();
}

function leave_ss(ss)
{
	this.strokeStyle = ss;
	this.stroke();
	this.restore();
}

function ceu()
{
	this.enter();
		this.ceu_caminho();
		let luz = this.createLinearGradient(64, 0, 64, 128);
		luz.addColorStop(0, 'yellow');
		luz.addColorStop(.2, 'orange');
		luz.addColorStop(.40,'cyan');
		luz.addColorStop(.60,'cyan');
		luz.addColorStop(.7, 'blue');
		luz.addColorStop(1, 'black');
	this.leave_fs(luz);
}

function ceu_caminho()
{
	this.beginPath();
		this.moveTo(0,0);
		this.lineTo(128,0);
		this.lineTo(128,128);
		this.lineTo(0,128);
		this.lineTo(0,0);
	this.closePath();
}

function cerca_ovelha()
{
	this.enter();
		this.la();
	this.leave_fs('rgb(234,234,174)');

	this.cerca();

	this.ovelha();
}

function la()
{
	this.beginPath();
		this.moveTo(70 /*+ parameters*/,80);
		this.quadraticCurveTo(67,75, 70/* + parameters*/,70);
		this.quadraticCurveTo(72,58, 80/* + parameters*/,60);
		this.quadraticCurveTo(85,57, 90/* + parameters*/,60);
		this.quadraticCurveTo(95,57, 100/* + parameters*/,60);
		this.quadraticCurveTo(112,58, 110/* + parameters*/,70);
		this.quadraticCurveTo(117,75, 110/* + parameters*/,80);
	this.closePath();
}

function cerca()
{
	for (let x = 0; x < 130; x += 10)
	{
		this.enter();
			this.tabua_vertical(x);
		this.leave_fs_ss('rgb(184,115,51)', 'rgb(92,64,51)');
	}

	for (var y = 85; y <= 110; y += 20)
	{
		this.enter();
			this.tabua_horizontal(y);
		this.leave_fs_ss('rgb(184,115,51)', 'rgb(92,64,51)');
	}

	for (var y = 88.5; y <= 110; y += 20)
	{
		if (y === 88.5)
		{
			for (var x = 5; x <= 130; x += 20)
			{
				this.enter();
					this.parafuso(x, y);
				this.leave_fs('rgb(168,168,168)');
			}
		}
		else
		{
			for (var x = 15; x <= 130; x += 20)
			{
				this.enter();
					this.parafuso(x, y);
				this.leave_fs('rgb(168,168,168)');
			}
		}
	}
}

function tabua_vertical(x)
{
	this.beginPath();
		this.moveTo(x,128);
		this.lineTo(x,80);
		this.quadraticCurveTo(x + 5,73, x + 10,80);
		this.lineTo(x + 10,128);
	this.closePath();
}

function tabua_horizontal(y)
{
	this.beginPath();
		this.rect(0,y, 128,7);
	this.closePath();
}

function parafuso(x,y)
{
	this.beginPath();
		this.arc(x,y, 1, 0,360, true);
	this.closePath();
}

function chao()
{
	this.beginPath();
		this.moveTo(0,128);
		this.lineTo(0,125);
	for (var i = 0; i < 128; i += 12)
	{
			this.lineTo(i + 2,125);
			this.quadraticCurveTo(i + 0,118, i + 0,123);
			this.quadraticCurveTo(i + 2,118, i + 5,123);
			this.quadraticCurveTo(i + 10,118, i + 10,123);
			this.quadraticCurveTo(i + 10,118, i + 8,125);
			this.lineTo(i + 12,125);
	}
		this.lineTo(i + 12,128);
		this.lineTo(i + 0,128);
	this.closePath();
}

function ovelha()
{
	this.cara_ovelha();

	this.enter_t_s(89,25, .4,.4);
		this.balao();
	this.leave_fs_ss('white', 'black');

	this.enter_t_s(103,35, .2,.2);
		this.texto_2();
	this.leave_fs('black');

	this.enter_t_s(116,38, .17,.17);
		this.texto_0_p2();

	this.leave_fs('black');

	this.enter_t_s(116,38, .17,.17);
		this.texto_0_p1();
	this.leave_fs('white');
}

function cara_ovelha()
{
	this.enter();
		this.contorno_cara_ovelha();
	this.leave_fs('black');

	this.enter();
		this.boca_ovelha();
	this.leave_fs('red');

	this.olhos_ovelha();

	this.enter_t_s(38,15, 1,1);
		this.corno();
	this.leave_ss('#996600');

	this.enter_t_s(142,15, -1,1);
		this.corno();
	this.leave_ss('#996600');
}

function contorno_cara_ovelha()
{
	this.beginPath();
		this.arc(90,73, 10, 0,360, false);
	this.closePath();
}

function boca_ovelha()
{
	this.beginPath();
		this.arc(95,78, 2, 0,360, false);
	this.closePath();
}

function olhos_ovelha()
{
	this.enter();
		this.esclerotida();
	this.leave_fs('white')

	this.enter();
		this.pupila();
	this.leave_fs('black');

	this.enter_t_s(-9,0, 1,1);
		this.esclerotida();
	this.leave_fs('white');

	this.enter_t_s(-9,0, 1,1);
		this.pupila();
	this.leave_fs('black');
}

function esclerotida()
{
	this.beginPath();
		this.arc(95,71, 3, 0,360, false);
	this.closePath();
}

function pupila()
{
	this.beginPath();
		this.arc(97,70, 1, 0,360, false);
	this.closePath();
}

function corno()
{
	this.beginPath();
		this.moveTo(40,50);
		this.quadraticCurveTo(41,48, 42,50);
		this.quadraticCurveTo(39,54, 38,50);
		this.quadraticCurveTo(41,44, 44,50);
		this.quadraticCurveTo(39,58, 36,50);
		this.moveTo(0,0);
	this.closePath();
}

function balao()
{
	this.beginPath();
	    this.moveTo(75, 25);
	    this.quadraticCurveTo(25, 25, 25, 62.5);
	    this.quadraticCurveTo(25, 100, 50, 100);
	    this.quadraticCurveTo(50, 120, 30, 125);
	    this.quadraticCurveTo(60, 120, 65, 100);
	    this.quadraticCurveTo(125, 100, 125, 62.5);
	    this.quadraticCurveTo(125, 25, 75, 25);
    this.closePath();
}

function texto_2()
{
	this.beginPath();
		this.moveTo(0,128);
		this.quadraticCurveTo(20,80, 55,60);
		this.quadraticCurveTo(65,50, 55,40);
		this.quadraticCurveTo(40,30, 30,50);
		this.lineTo(10,45);
		this.quadraticCurveTo(15,15, 55,20);
		this.quadraticCurveTo(60,20, 65,22);
		this.quadraticCurveTo(100,40, 55,80);
		this.quadraticCurveTo(30,100, 30,115)
		this.lineTo(80,115);
		this.lineTo(80,128);
		this.lineTo(0,128);
	this.closePath();
}

function texto_0_p1()
{
	this.beginPath();
		this.moveTo(45,88);
		this.quadraticCurveTo(38,64, 45,40);
		this.quadraticCurveTo(50,25, 60,20);
		this.quadraticCurveTo(64,18, 68,20);
		this.quadraticCurveTo(78,25, 83,40);
		this.quadraticCurveTo(90,64, 83,88);
		this.quadraticCurveTo(78,103, 68,108);
		this.quadraticCurveTo(64,110, 60,108);
		this.quadraticCurveTo(50,103, 45,88);
	this.closePath();
}

function texto_0_p2()
{
	this.beginPath();
		this.moveTo(25,108);
		this.quadraticCurveTo(10,64, 25,20);
		this.quadraticCurveTo(30,5, 40,0);
		this.quadraticCurveTo(64,-8, 88,0);
		this.quadraticCurveTo(98,5, 103,20);
		this.quadraticCurveTo(120,64, 103,108);
		this.quadraticCurveTo(98,123, 88,128);
		this.quadraticCurveTo(64,136, 40,128);
		this.quadraticCurveTo(30,123, 25,108);
	this.closePath();
}

function pessoa()
{
	this.braco();

	this.cabeca();

	this.enter_t_s(0,-20, 1,1);
		this.tronco();
	this.leave_fs('rgb(50,205,50)');

	this.perna();

	this.enter_t_s(0,-20, 1,1);
		this.calcoes();
	this.leave_fs('rgb(255,165,0)');

	this.enter_t_s(-30,80, 1,1);
		this.rotate(-44.8)
		this.perna();
	this.leave();

	this.enter_t_s(0,85, 1,1);
		this.rotate(-45);
		this.braco();
	this.leave();
}

function braco()
{
	this.enter_t_s(14.5,1, .7,.7);
		this.mao();
	this.leave_fs_ss('rgb(235,199,158)', 'rgb(184,115,51)');

	this.enter_t_s(0,-20, 1,1);	
		this.contorno_braco();
	this.leave_ss('rgb(235,199,158)');

	this.enter_t_s(0,-20, 1,1);
		this.manga();
	this.leave_fs('rgb(50,205,50)');
}

function mao()
{
	this.beginPath();
		this.moveTo(47,73);
		this.quadraticCurveTo(44,69, 42,70);
		this.quadraticCurveTo(40,68, 39,66);
		this.lineTo(37,62);
		this.quadraticCurveTo(38,61, 39,61);
		this.lineTo(42,65);
		this.lineTo(39, 58);
		this.quadraticCurveTo(40,57, 41,57);
		this.lineTo(44.5,63);
		this.lineTo(41,54);
		this.quadraticCurveTo(42,53, 43,53);
		this.lineTo(46.5,61);
		this.lineTo(44,54);
		this.quadraticCurveTo(45,53, 46,53);
		this.lineTo(50,63);
		this.quadraticCurveTo(53,66, 53,58);
		this.quadraticCurveTo(54,56, 55,58);
		this.lineTo(55,63);
		this.quadraticCurveTo(55,65, 53,68);
	this.closePath();
}

function contorno_braco()
{
	this.lineCap = 'round';
	this.lineJoin = 'round';
	this.lineWidth = 7;
	this.beginPath();
		this.moveTo(50,70);
		this.lineTo(60,80);
		this.lineTo(75,65);
}

function manga()
{
	this.lineJoin = 'round';
	this.beginPath();
		this.moveTo(70,60);
		this.quadraticCurveTo(85,55, 80,70)
	this.closePath();
}

function tronco()
{
	this.beginPath();
		this.moveTo(72.5,60);
		this.quadraticCurveTo(67.2,77.5, 72.5,90);
		this.lineTo(82.5,90);
		this.lineTo(82.5,60);
		this.quadraticCurveTo(77.5,65, 72.5,60);
	this.closePath();
}


function calcoes()
{
	this.beginPath();
		this.moveTo(72.5,90);
		this.lineTo(72.5,95);
		this.lineTo(82.5,95);
		this.lineTo(82.5,90);
	this.closePath();
}

function perna()
{
	this.enter();
		this.coxa();
	this.leave_ss('rgb(255,165,0)');

	this.enter();
		this.canela();
	this.leave_ss('rgb(255,165,0)');

	this.enter();
		this.meia();
	this.leave_fs_ss('white', 'blue');

	this.enter();
		this.sapato();
	this.leave_ss('blue');
}

function coxa()
{
	this.lineCap = 'round';
	this.lineWidth = 10;
	this.beginPath();
		this.moveTo(78,75);
		this.lineTo(55,90);
}

function canela()
{
	this.lineCap = 'round';
	this.lineWidth = 9;
	this.beginPath();
		this.moveTo(55,90);
		this.lineTo(70,115);
}

function meia()
{
	this.beginPath();
		this.moveTo(61,110);
		this.lineTo(67,120);
		this.lineTo(76,115);
		this.lineTo(70,105);
	this.closePath();
}

function sapato()
{
	this.lineCap = 'round';
	this.lineWidth = 9;
	this.beginPath();
		this.moveTo(74,117);
		this.lineTo(65,121);
}

function cabeca()
{
	this.enter();
		this.contorno_cara_pessoa();
	this.leave_fs('rgb(235,199,158)');

	this.enter_t_s(-1,1, 1,1)
		this.olho_pessoa();
	this.leave();

	this.enter_t_s(-1,1, 1,1);
		this.sobrancelha();
	this.leave_ss('brown');

	this.enter();
		this.boca_pessoa();
	this.leave_fs('red');

	this.enter();
		this.cabelo();
	this.leave_fs('brown');

	this.enter();
		this.orelha();
	this.leave_fs_ss('rgb(235,199,158)', 'rgb(184,115,51)');

	this.enter_t_s(70,22, .08,.08);
		this.gota();
	this.leave_fs('rgb(0,127,255)');

	this.enter_t_s(68,24, .08,.08);
		this.gota();
	this.leave_fs('rgb(0,127,255)');
}

function contorno_cara_pessoa()
{
	this.beginPath();
		this.moveTo(74,40);
		this.lineTo(74,35);
		this.quadraticCurveTo(74,33, 70,33);
		this.quadraticCurveTo(66,33, 66,27);
		this.lineTo(63,27);
		this.quadraticCurveTo(68,23, 68,18);
		this.quadraticCurveTo(75,12, 82.5,18);
		this.quadraticCurveTo(85,25.5, 82.5,32);
		this.quadraticCurveTo(81,34, 80,33);
		this.quadraticCurveTo(79,34, 79,40);
	this.closePath();
}

function olho_pessoa()
{
	this.enter();
		this.esclerotida_pessoa();
	this.leave_fs('white');

	this.enter_t_s(-7,-33, .8,.8);
		this.pupila();
	this.leave_fs('blue');
}

function esclerotida_pessoa()
{
	this.lineJoin = 'round';
	this.beginPath();
		this.moveTo(69,23);
		this.quadraticCurveTo(71,20, 73,23);
		this.quadraticCurveTo(71,26, 69,23);
	this.closePath();
}

function sobrancelha()
{
	this.beginPath();
		this.moveTo(69,20);
		this.quadraticCurveTo(72,19, 74,21);
}

function gota()
{
	this.beginPath();
		this.moveTo(64,20);
		this.quadraticCurveTo(62,35, 54,40);
		this.quadraticCurveTo(45,60, 64,70);
		this.quadraticCurveTo(83,60, 74,40);
		this.quadraticCurveTo(66,35, 64,20);
	this.closePath();
}

function boca_pessoa()
{
	this.beginPath();
		this.moveTo(66.5,28);
		this.quadraticCurveTo(68,28.5, 71,30);
		this.lineTo(66.5,30);
	this.closePath();
}

function orelha()
{
	this.lineWidth = .5;
	this.beginPath();
		this.moveTo(76,27);
		this.quadraticCurveTo(77.5,25, 79,27);
		this.quadraticCurveTo(81,28.5, 77,30);
}

function cabelo()
{
	this.beginPath();
		this.moveTo(68,18);
		this.quadraticCurveTo(75,12, 82.5,18);
		this.quadraticCurveTo(85,25.5, 82.5,32);
		this.quadraticCurveTo(81,34, 80,33);
		this.lineTo(79,27);
		this.lineTo(76,27);
		this.lineTo(76,29);
		this.lineTo(75,29);
		this.lineTo(75,27);
		this.quadraticCurveTo(75,18, 68,18);
	this.closePath();
}