<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <?php

            if($review->review)
            {
              if($review->category == 0)
                $image = '/img/gota-verde-movilidad.png';
              else if($review->category == 1)
                $image = '/img/gota-verde-visual.png';
              else if($review->category == 2)
                $image = '/img/gota-verde-auditivo.png';
              else
                $image = '/img/gota-verde-intelectual.png';  

              $color = '0x37ab2e';
            }
            else
            {
              if($review->category == 0)
                $image = '/img/gota-roja-movilidad.png';
              else if($review->category == 1)
                $image = '/img/gota-roja-visual.png';
              else if($review->category == 2)
                $image = '/img/gota-roja-auditivo.png';
              else
                $image = '/img/gota-roja-intelectual.png'; 


              $color = '0xa72d2d';  
            }

    ?>
    <div class="container">
      <div class="row">
        <div class="col-md-4">
          <img class='img-responsive' src="http://maps.googleapis.com/maps/api/staticmap?center={{$review->lat}},{{$review->lng}}&zoom=13&size=300x600&maptype=roadmap&markers=color:{{$color}}%7Clabel:S%7C{{$review->lat}},{{$review->lng}}&sensor=false" alt="">
        </div>
        <div class="col-md-4">
          <div class="page-header">
            @if(!$review->picture)
            <img class='img-responsive' src="http://11870.com/multimedia/imagenes/casa-puga_pxl_6531251392f5f4a4c558fca7f74c1ae9.jpeg" alt="">
            @else
            <img class='img-responsive' src="/img/review{{$review->id}}.jpg" alt="">
            @endif
            <h1><img src="{{$image}}" alt=""> {{$review->title}} <small>{{$review->created_at}}</small></h1>
          </div>
            <p>{{$review->body}}</p>          
        </div>

        <div class="col-md-4">
          <h2><i class='fa fa-lightbulb-o'></i> Soluciones</h2>
          <hr>
            @if(count($solutions)<=0)
            <div class="alert alert-info">No hay ninguna solución propuesta, ¿Por qué no propones una?</div>
            @else
              @foreach($solutions as $row)
                <div>
                  <strong>{{$row->name}}</strong> ({{$row->telephone}})
                  <p>{{$row->body}}</p>
                </div>
              @endforeach
            @endif
          <hr>
                {{ Form::open(array('url' => URL::route('reviews.solutions.store',$review->id),'class'=>'form-horizontal')) }}
                  <fieldset>
                    <div class="form-group">
                      <label class="col-md-6 control-label">Nombre de la empresa</label>
                      <div class='col-sm-9 col-md-6'>
                        {{ Form::text('name', Input::old('name'), array('placeholder' => 'Soluciones Accesibles SL','class'=>'form-control')) }}
                        @if($errors->has('name'))
                          <div class='alert alert-danger'>{{ $errors->first('name') }}</div>
                        @endif
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-md-6 control-label">Descripción de la solución</label>
                      <div class='col-sm-9 col-md-6'>
                        {{ Form::textarea('body', Input::old('body'), array('placeholder' => 'Rampa metálica plegable','class'=>'form-control')) }}
                        @if($errors->has('body'))
                          <div class='alert alert-danger'>{{ $errors->first('body') }}</div>
                        @endif
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-md-6 control-label">Teléfono de contacto</label>
                      <div class='col-sm-9 col-md-6'>
                        {{ Form::text('telephone', Input::old('telephone'), array('placeholder' => '+626 51 47 70','class'=>'form-control')) }}
                        @if($errors->has('telephone'))
                          <div class='alert alert-danger'>{{ $errors->first('telephone') }}</div>
                        @endif
                      </div>
                    </div>        
                    <div class="form-actions">
                      {{ Form::submit('Proponer', array('class'=>'btn btn-primary btn-sm')) }}
                    </div>
                  </fieldset>
                {{ Form::close() }}          
        </div>
      </div>
    </div>


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="/js/bootstrap.min.js"></script>
  </body>
</html> 
  