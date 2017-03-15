package baseline;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class NaiveBayesBaseline {

	public void clasificar(Instances pSetTrain, Instances pSetTest) throws Exception {
		NaiveBayes estimador = new NaiveBayes();
		// Construimos el evaluador con los datos de entrenamiento
		Evaluation evaluador = new Evaluation(pSetTrain);
		evaluador.evaluateModel(estimador, pSetTrain);
		for (int i = 0; i < pSetTest.numInstances(); i++) {
			System.out.println(evaluador.evaluateModelOnce((Classifier) estimador, pSetTest.instance(i)));
		}
	}
}
