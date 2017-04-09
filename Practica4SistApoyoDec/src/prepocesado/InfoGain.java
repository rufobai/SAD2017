package prepocesado;

import java.io.File;
import java.io.FileReader;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class InfoGain {
	public static void main(String[] args) throws Exception {
		//comprueba que se reciban 3 archivos en orden(train, dev y test) 
				if(args.length==2){
					//Carga archivos
					Instances train = new Instances(new FileReader(args[0]));
					System.out.println(">>Datos train cargados.");
					
					//filtro BOW (outPutWordsCounts, lowerCase, rainbow)
					StringToWordVector filterWordVector = new StringToWordVector();
					filterWordVector.setInputFormat(train);
					filterWordVector.setAttributeIndices(""+(train.attribute("class").index()));
					filterWordVector.setAttributeNamePrefix("sms_");
					filterWordVector.setTFTransform(false);
					filterWordVector.setIDFTransform(false);
					filterWordVector.setOutputWordCounts(true);
					filterWordVector.setWordsToKeep(2000);
					filterWordVector.setLowerCaseTokens(true);
					//Batch Filtering
					Instances newTrain = Filter.useFilter(train, filterWordVector);
					System.out.println("Filtro BOW aplicado."); 
					System.out.println(newTrain.get(0));
					//filtro FSS-Infogain
					AttributeSelection atributeSelection = new AttributeSelection();
					newTrain.setClassIndex(newTrain.attribute("class").index());
					atributeSelection.setInputFormat(newTrain);
					//InfoGain (valores por defecto de Weka)
					InfoGainAttributeEval infoGain = new InfoGainAttributeEval();
					infoGain.setBinarizeNumericAttributes(false);
					infoGain.setMissingMerge(true);
					atributeSelection.setEvaluator(infoGain);
					//Ranker
					Ranker ranker= new Ranker();
					ranker.setGenerateRanking(true);
					ranker.setThreshold(0.01);
					atributeSelection.setSearch(ranker);
					//Batch Filtering
					newTrain = Filter.useFilter(newTrain, atributeSelection);	
					System.out.println("Filtro InfoGain aplicado.");
					
					//guarda los nuevo arff (en mismo path que los arff de entrada)
					ArffSaver newArff = new ArffSaver();
					newArff.setInstances(newTrain);
					newArff.setFile(new File(args[1].toString()));
					newArff.writeBatch();
					System.out.println(">>Datos train guardados.");
					
				}else{
					System.out.println("Necesarios 2 archivos el .arff y la ruta donde se guardará");
				}
			}
		}
