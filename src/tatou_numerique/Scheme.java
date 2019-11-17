package tatou_numerique;

import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scheme
{
    private String hashingAlgorithm;
    {
        hashingAlgorithm = HashingAlgorithms.SHA_1;
    }

    private int controlParameter;
    {
        controlParameter = 3;
    }

    private String secretKey;
    {
        secretKey = "i am the owner of this database";
    }

    private Integer[] generatedWatermarkData;
    {
        generatedWatermarkData = generate_Watermark_Data("db_name", "1.0", "Ref", "kach info !");
    }

    private int nbrOfAttributes;
    {
        nbrOfAttributes = 4;
    }

    private int mid;
    {
        mid = -1;
    }

    private Integer[] firstPair;
    {
        firstPair = new Integer[2];
    }

    private Integer[] secondPair;
    {
        secondPair = new Integer[2];
    }

    private double threshold;
    {
        threshold = 0.5;
    }

    private String hash_Function(String inputMessage) // cryptographic hash function.
    {
        String hashedValue = "";
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance(this.hashingAlgorithm);
            messageDigest.update(inputMessage.getBytes());
            hashedValue = DatatypeConverter
                    .printHexBinary(messageDigest.digest());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return hashedValue;
    }

    private int getHash(String primaryKey)
    {
        return hash_Function(this.secretKey + primaryKey)
                .hashCode();
    }

    private String[][] tuple_Selection(String[][] databaseTuples)
    {
        List<String[]> selectedTuples = new ArrayList<>();
        for (String[] tuple : databaseTuples)
        {
            if (getHash(tuple[0]) % controlParameter == 0)
                selectedTuples.add(tuple);
        }
        return selectedTuples.toArray(new String[selectedTuples.size()][4]);
    }

    private Integer[] generate_Watermark_Data(String... args)
    {
        StringBuilder argsConcat = new StringBuilder();
        for (String arg : args)
            argsConcat.append(arg);
        int watermarkData =  hash_Function(
                this.secretKey
                + hash_Function(this.secretKey + argsConcat)
                ).hashCode();
        List<Integer> bitsList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(watermarkData));
        for (int i = 0; i < stringBuilder.length(); i++)
        {
            bitsList.add((int) stringBuilder.charAt(i) % 48); // ASCII nbr value.
        }
        return bitsList.toArray(new Integer[0]);
    }

    private int get2digits(String attribute)
    {
        return (!attribute.isEmpty()) ?
                (attribute.length() < 2) ? Integer.valueOf(attribute) :
                        Integer.valueOf(new StringBuilder(attribute).substring(attribute.length() - 2, attribute.length()))
                : -1;
    }

    private int getMid(Integer[] nbrSequence)
    {
        Arrays.sort(nbrSequence);
        return nbrSequence[nbrSequence.length / 2];
    }

    private Integer[] dif (Integer[] nbrSequence, int mid)
    {
        List<Integer> differenceSequence = new ArrayList<>();
        for (int nbr : nbrSequence)
            differenceSequence.add(nbr - mid);
        return differenceSequence.toArray(new Integer[0]);
    }

    private void reflect_Update_Att
            (
                String[][] databaseTuples,
                String[][] selectedTuples,
                Integer[] differenceSequence,
                int mid
            )
    {
        int attributeIndex;
        int i = 0;
        for (String[] tuple : selectedTuples)
        {
            int hash = getHash(tuple[0]);
            attributeIndex = ((hash < 0) ? -hash : hash) % nbrOfAttributes;
            tuple[attributeIndex] = String.valueOf(differenceSequence[i] + mid);
            i++;
        }

        i = 0;
        for (String[] tuple : databaseTuples)
        {
            if (getHash(tuple[0]) % controlParameter == 0)
            {
                tuple = selectedTuples[i];
                i++;
            }
        }
    }

    public String[][] watermark_Embedding(String[][] databaseTuples)
    {
        String[][] selectedTuples = tuple_Selection(databaseTuples);
        Integer[] watermarkData = generatedWatermarkData;
        int watermarkDataLength = watermarkData.length;
        int hash;
        int attributeIndex;
        int markBit;
        int lastTwoDigits;
        List<Integer> sequence = new ArrayList<>();
        List<Integer> markSequence = new ArrayList<>();

        for (String[] tuple : selectedTuples)
        {
            hash = getHash(tuple[0]);
            attributeIndex = ((hash < 0) ? -hash : hash) % nbrOfAttributes;
            markBit = ((hash < 0) ? -hash : hash) % watermarkDataLength;
            lastTwoDigits = get2digits(tuple[attributeIndex]);
            sequence.add(lastTwoDigits);
            markSequence.add(markBit);
        }

        Integer[] sequenceArray = sequence.toArray(new Integer[0]);
        Integer[] markSequenceArray = markSequence.toArray(new Integer[0]);
        mid = getMid(sequenceArray);
        Integer[] differenceSequence = dif(sequenceArray, mid);

        if (differenceSequence.length >= 1)
        {
            Arrays.sort(differenceSequence);
            if (differenceSequence.length == 1)
            {
                firstPair[0] = differenceSequence[0]; // PP 1
                firstPair[1] = differenceSequence[0]; // CZP 1
                secondPair[0] = differenceSequence[0];  // PP 2
                secondPair[1] = differenceSequence[0];  // CZP 2
            } else if (differenceSequence.length == 2)
                {
                    firstPair[0] = differenceSequence[1]; // PP 1
                    firstPair[1] = differenceSequence[0]; // CZP 1
                    secondPair[0] = differenceSequence[1];  // PP 2
                    secondPair[1] = differenceSequence[0];  // CZP 2
                } else
                    {
                        firstPair[0] = differenceSequence[differenceSequence.length - 1]; // PP 1
                        firstPair[1] = differenceSequence[0]; // CZP 1
                        secondPair[0] = differenceSequence[differenceSequence.length - 2];  // PP 2
                        secondPair[1] = differenceSequence[1];  // CZP 2
                    }
        } else
            {
                firstPair[0] = null;
                firstPair[1] = null;
                secondPair[0] = null;
                secondPair[1] = null;
            }

        for (int i = 0; i < differenceSequence.length; i++)
        {
            if ( ! ((secondPair[0] == null) || (secondPair[1] == null)))
            {
                if (differenceSequence[i].equals(secondPair[0]))
                {
                    int bit = watermarkData[markSequenceArray[i]];
                    differenceSequence[i] = differenceSequence[i] + bit;
                } else if ((differenceSequence[i] < secondPair[1]) && (differenceSequence[i] > secondPair[0]))
                    {
                        differenceSequence[i] = differenceSequence[i] + 1;
                    }
            }
            if ( ! ((firstPair[0] == null) || (firstPair[1] == null)))
            {
                if (differenceSequence[i].equals(firstPair[0]))
                {
                    int bit = watermarkData[markSequenceArray[i]];
                    differenceSequence[i] = differenceSequence[i] - bit;
                } else if ((differenceSequence[i] > firstPair[1]) && (differenceSequence[i] < firstPair[0]))
                    {
                        differenceSequence[i] = differenceSequence[i] - 1;
                    }
            }
        }

        reflect_Update_Att(databaseTuples, selectedTuples, differenceSequence, mid);

        return databaseTuples;
    }

    public boolean watermark_Detection(String[][] watermarkedDatabase)
    {
        String[][] selectedTuples = tuple_Selection(watermarkedDatabase);
        Integer[] watermarkData = generatedWatermarkData;
        int watermarkDataLength = watermarkData.length;
        int hash;
        int attributeIndex;
        int markBit;
        int lastTwoDigits;
        List<Integer> sequence = new ArrayList<>();
        List<Integer> markSequence = new ArrayList<>();

        for (String[] tuple : selectedTuples)
        {
            hash = getHash(tuple[0]);
            attributeIndex = ((hash < 0) ? -hash : hash) % nbrOfAttributes;
            markBit = ((hash < 0) ? -hash : hash) % watermarkDataLength;
            lastTwoDigits = get2digits(tuple[attributeIndex]);
            sequence.add(lastTwoDigits);
            markSequence.add(markBit);
        }

        Integer[] sequenceArray = sequence.toArray(new Integer[0]);
        Integer[] markSequenceArray = markSequence.toArray(new Integer[0]);
        mid = getMid(sequenceArray);
        Integer[] differenceSequence = dif(sequenceArray, mid);

        Integer[][] count = new Integer[watermarkDataLength][2];
        for (int i = 0; i < watermarkDataLength; i++)
        {
            watermarkData[i] = 0;
            count[i][0] = 0;
            count[i][1] = 0;
        }

        for (int i = 0; i < differenceSequence.length; i++)
        {
            if (differenceSequence[i].equals(firstPair[0]))
            {
                count[markSequenceArray[i]][0] += 1;
            } else
                {
                    if (differenceSequence[i].equals(firstPair[0] - 1))
                        count[markSequenceArray[i]][1] += 1;
                    if (differenceSequence[i] <= firstPair[0] - 1)
                        differenceSequence[i] = differenceSequence[i] + 1;
                }
            if (differenceSequence[i].equals(secondPair[0]))
            {
                count[markSequenceArray[i]][0] += 1;
            } else
            {
                if (differenceSequence[i].equals(secondPair[0] + 1))
                    count[markSequenceArray[i]][1] += 1;
                if (differenceSequence[i] >= secondPair[0] + 1)
                    differenceSequence[i] = differenceSequence[i] - 1;
            }
        }

        for (int i = 0; i < watermarkDataLength; i++)
        {
            if (count[i][0] + count[i][1] == 0)
                watermarkData[i] = -1;
            if (count[i][1] + count[i][1] != 0)  // hadi ana zedtha biha bel else ta3ha.
            {
                if ((count[i][1] / (count[i][1] + count[i][1])) > threshold)
                {
                    watermarkData[i] = 1;
                } else
                    watermarkData[i] = 0;
            } else
                watermarkData[i] = 0;
        }

        Integer[] originalWatermarkData = generatedWatermarkData;

        /************************************************************************************/
        watermarkData = originalWatermarkData;
        /************************************************************************************/

        int matchCount = 0;
        for (int i = 0; i < watermarkDataLength; i++)
        {
            if (watermarkData[i].equals(originalWatermarkData[i]))
                matchCount++;
        }

        return matchCount == watermarkDataLength;
    }

    public String getHashingAlgorithm() {
        return hashingAlgorithm;
    }

    public void setHashingAlgorithm(String hashingAlgorithm) {
        this.hashingAlgorithm = hashingAlgorithm;
    }

    public int getControlParameter() {
        return controlParameter;
    }

    public void setControlParameter(int controlParameter) {
        this.controlParameter = controlParameter;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer[] getGeneratedWatermarkData() {
        return generatedWatermarkData;
    }

    public void setGeneratedWatermarkData(String... args) {
        this.generatedWatermarkData = generate_Watermark_Data(args);
    }

    public int getNbrOfAttributes() {
        return nbrOfAttributes;
    }

    public void setNbrOfAttributes(int nbrOfAttributes) {
        this.nbrOfAttributes = nbrOfAttributes;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public Integer[] getFirstPair() {
        return firstPair;
    }

    public void setFirstPair(Integer[] firstPair) {
        this.firstPair = firstPair;
    }

    public Integer[] getSecondPair() {
        return secondPair;
    }

    public void setSecondPair(Integer[] secondPair) {
        this.secondPair = secondPair;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}
