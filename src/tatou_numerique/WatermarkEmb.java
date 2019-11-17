package tatou_numerique;

import java.util.Random;

public class WatermarkEmb
{

    String[][] tuples;
    String[][] tuplesResult;
    private final int secretKey = 110525;

    public WatermarkEmb(String[][] vals)
    {
        this.tuples = vals;
        doAlgorithm();
    }

    private void doAlgorithm()
    {
        tuplesResult = embedWatermarks(tuples, secretKey);
    }

    public String[][] getResutas()
    {
        return tuplesResult;
    }

    public String mark(int randomNbr, int attributeValue, int bitIndex)
    {
        String markedValue = "";
        if (randomNbr % 2 == 0) // if the random number is even. (pair/impair ?)
        {
            /** if the attributeValue bit of index [bitIndex] is not 0, then set the bit to 0 */
            markedValue = (((attributeValue >> bitIndex) & 1) == 0) ?
                    new Integer(attributeValue).toString() :
                    new Integer(attributeValue - bitIndex * 2).toString();
        } else
        {
            /** if the attributeValue bit of index [bitIndex] is not 1, then set the bit to 1 */
            markedValue = (((attributeValue >> bitIndex) & 1) == 1) ?
                    new Integer(attributeValue).toString() :
                    new Integer(attributeValue - bitIndex * 2).toString();
        }
        return markedValue;
    }

    public String[][] embedWatermarks(String[][] tuples, int secretKey)
    {
        System.out.println("enter !!");

        Random generator = new Random(); /* pseudo-random sequence number generator */
        int nbrOfTuples = tuples.length;
        int controlParameter = 3; /* note that the control parameter is private to the owner */

        for (int i = 0; i < nbrOfTuples; i++)
        {
            String primaryKey = tuples[i][0]; /* primary key attribue of the current tuple */

            /**
             * initialize the generator with new seed.
             * the new seed is the concatenation of the primaryKey attribute and the secret key of the owner.
             * this initialization ensures that the output will be distinct for any given distinct tuple.
             */
            generator.setSeed(Integer.parseInt(primaryKey + secretKey));

            if (generator.nextInt() % controlParameter == 0)                  // mark this tuple.
            {
                int attributesNbr = 0;
                for (String[] tuple : tuples)
                {
                    for (String attrribute : tuple)
                    {
                        attributesNbr++;
                    }
                    break;
                }
                int attributeIndex = generator.nextInt() % attributesNbr;     // generated attribute index for marking.
                if (attributeIndex < 0) attributeIndex = -attributeIndex;     // ensure that the index is positive.

                int nbrOfBits = 8; //number of least significant bits available for marking in an attribute.

                int bitIndex = generator.nextInt() % nbrOfBits;               // generated bit index for marking.
                if (bitIndex < 0) bitIndex = -bitIndex;                       // ensure that the index is positive.

                String attributeValue = tuples[i][attributeIndex];
                /**
                 * mark the random bit of the random attribute value of the current tuple
                 *  according to the new generated nbr.
                 */
                tuples[i][attributeIndex] = mark(generator.nextInt(), Integer.parseInt(attributeValue), bitIndex);
            }
        }
        return tuples;
    }
    
}
