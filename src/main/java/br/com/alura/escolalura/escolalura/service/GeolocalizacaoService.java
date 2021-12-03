package br.com.alura.escolalura.escolalura.service;

import br.com.alura.escolalura.escolalura.models.Contato;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class GeolocalizacaoService {

    public List<Double> obterLatELongPor(Contato contato) throws IOException, InterruptedException, ApiException {
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDEu3gwXxqiLunLhQnFqmu3zEZiPYoGV60");

        GeocodingApiRequest request = GeocodingApi.newRequest(context).address(contato.getEndereco());

        GeocodingResult[] results = request.await();

        GeocodingResult resultado = results[0];

        Geometry geometry = resultado.geometry;

        LatLng location = geometry.location;

        return Arrays.asList(location.lat, location.lng);
    }

}
